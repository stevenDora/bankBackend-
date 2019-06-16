package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.annotate.AccessLimit;
import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.constant.PayApiEnum;
import cn.stylefeng.guns.modular.system.dto.*;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.IBankRemarkService;
import cn.stylefeng.guns.modular.system.service.ITradeService;
import cn.stylefeng.guns.modular.system.service.PayApiService;
import cn.stylefeng.guns.modular.system.utils.DateUtil;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.RedisOrderPrefix.ORDER_DETAIL;
import static cn.stylefeng.guns.modular.system.constant.Constant.RedisOrderPrefix.ORDER_DUPLICATE_SUBMIT_CHECK;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.PushStatus.PUSH_STATUS_UNHANDLE;


@Service
public class PayApiServiceImpl implements PayApiService {

    private static Logger logger = LoggerFactory.getLogger(PayApiServiceImpl.class);


    @Autowired
    private IBankCardService bankCardService;

    @Autowired
    private ITradeService tradeService;

    @Autowired
    private RedisDao redisDao;

    private String PAYLINK_PREFIX = "http://localhost:8080/payApi/counter/";

    @Autowired
    private IBankRemarkService bankRemarkService;

/*    @AccessLimit(seconds = 5, maxCount = 5, type = ORDER_DUPLICATE_SUBMIT_CHECK)*/
    @Override
    public Object deposit(PayDepositReq req) {
        ResponseResult result = null;
        String payLink = PAYLINK_PREFIX;
        PayDepositRsp depositRsp = null;
        try{
            //重复提交校验
            String orderDupKey = req.getMerchantId()+"_"+req.getMerchantOrderNo();
            Long submit_times = redisDao.incr(ORDER_DUPLICATE_SUBMIT_CHECK, orderDupKey);
            if(submit_times > ORDER_DUPLICATE_SUBMIT_MAX){
                logger.info("merchant:{},merchant_order_no:{} submit duplicate..",
                        req.getMerchantId(),req.getMerchantOrderNo());

                return ResponseResult.builder()
                        .data(null)
                        .code(PayApiEnum.DEPOSIT_FAILED_MERCHANT_REPEAT_SUBMIT.getCode())
                        .msg(PayApiEnum.DEPOSIT_FAILED_MERCHANT_REPEAT_SUBMIT.getDesc()).build();
            }
            AccountSelectRsp accountSelectRsp = this.accountSelect(req);
            //生成订单id
            String orderNo = getOrderNo(req);

            //缓存订单
            Trade trade = initOrder(req,orderNo,accountSelectRsp);
            String order_detail_key = ORDER_DETAIL + orderNo;
            redisDao.set(order_detail_key, JSONObject.toJSONString(trade));
            redisDao.expire(order_detail_key,30*60);

            //异步入库
            this.createOrder(trade);
            payLink  = payLink + orderNo;
            depositRsp = PayDepositRsp.builder()
                    .amount(req.getAmount())
                    .merchant_id(req.getMerchantId())
                    .channel(req.getChannel())
                    .merchant_order_no(req.getMerchantOrderNo())
                    .payUrl(payLink)
                    .platform_order_no(orderNo)
                    .invalidDate(DateUtil.addMinute(new Date(), 10))
                    .build();
        }catch (ServiceException e){
            e.printStackTrace();
            if(e.getCode() == GENERATION_ORDER_NO_FAILED.getCode()){
                throw new ServiceException(GENERATION_ORDER_NO_FAILED);
            }
            else {
                throw new ServiceException(DEPOSIT_FAILED_UNKNOW);
            }

        }
        return ResponseResult.builder()
                .data(depositRsp)
                .code(PayApiEnum.DEPOSIT_APPLY_SUCCESS.getCode())
                .msg(PayApiEnum.DEPOSIT_APPLY_SUCCESS.getDesc()).build();
    }



    private Trade initOrder(PayDepositReq req,String orderNo,AccountSelectRsp rsp){
        Trade trade = Trade.builder()
                .applyAmount(req.getAmount())
                .actualAmount(new BigDecimal(0))
                .channel(Integer.valueOf(req.getChannel()))
                .companyNo(req.getMerchantId().intValue())
                .companyOrderNo(req.getMerchantOrderNo())
                .crtTime(new Date())
                .orderNo(orderNo)
                .pushTime(null)
                .arriveTime(null)
                .remark(rsp.getRemark())
                .serviceFee(new BigDecimal(0))
                .orderStatus(ORDER_STATUS_PENDING)
                .pushStatus(PUSH_STATUS_UNHANDLE)
                .accountId(rsp.getAccount_id())
                .accountInfo(rsp.getAccount_info()).build();

        return trade;
    }

    @Async
    @Transactional
    @Override
    public void createOrder(Trade trade){
        trade.setOrderStatus(ORDER_STATUS_PROCESS);
        tradeService.insert(trade);
    }

    @Override
    public Object getDepositDetail(String orderNo){
        String tradeStr = redisDao.get(orderNo);
        if(StringUtils.isEmpty(tradeStr)){
            //订单不存在
            if (syncTradeToRedis(orderNo))
                throw new ServiceException(DEPOSIT_FAILED_ORDER_NOT_EXIST);
        }
        Trade trade = JSONObject.parseObject(tradeStr, Trade.class);
        DepositAccountDetailRsp depositDetail = DepositAccountDetailRsp.builder().
                channel(trade.getChannel())
                .orderNo(trade.getOrderNo())
                .account(trade.getAccountInfo())
                .remark(trade.getRemark())
                .createDate(trade.getCrtTime())
                .overDueDate(trade.getOverDueTime()).build();

        if(trade.getChannel() == CHANNEL_BANK){
            String[] name_cardNo = depositDetail.getAccount().split("#");
            if(StringUtils.isEmpty(name_cardNo)||name_cardNo.length!=2){
                throw new ServiceException(DEPOSIT_FAILED_ORDER_NOT_EXIST);
            }
            //银行卡渠道-拆分成姓名+卡号
            depositDetail.setName(name_cardNo[0]);
            depositDetail.setAccount(name_cardNo[1]);
        }
        return ResponseResult.builder().data(depositDetail)
                .code(DEPOSIT_APPLY_SUCCESS.getCode())
                .msg(DEPOSIT_APPLY_SUCCESS.getDesc()).build();
    }


    @Override
    public Object getDepositResult(String orderNo) {
        String tradeStr = redisDao.get(orderNo);
        if(StringUtils.isEmpty(tradeStr)){
            //订单不存在
            if (syncTradeToRedis(orderNo))
                return ResponseResult.builder()
                    .data(null)
                    .code(DEPOSIT_FAILED_ORDER_NOT_EXIST.getCode())
                    .msg(DEPOSIT_FAILED_ORDER_NOT_EXIST.getDesc()).build();
        }

        Trade trade = JSONObject.parseObject(tradeStr, Trade.class);
        return ResponseResult.builder()
                .data(DepositApplyResult.builder()
                        .orderNo(trade.getOrderNo())
                        .channel(trade.getChannel())
                        .orderStatus(trade.getOrderStatus()).build())
                .code(DEPOSIT_APPLY_SUCCESS.getCode())
                .msg(DEPOSIT_APPLY_SUCCESS.getDesc()).build();
    }

    private boolean syncTradeToRedis(String orderNo) {
        Trade trade = tradeService.selectTradeByOrderNo(orderNo);
        if (null == trade) {
            redisDao.set(orderNo, "订单不存在");
            //防止redis穿透
            redisDao.expire(orderNo, 5 * 60);
            return true;
        }
        redisDao.set(orderNo, JSONObject.toJSONString(trade));
        return false;
    }


    private AccountSelectRsp accountSelect(PayDepositReq req){
        ResponseResult result = null;
        String account_info = "";
        AccountSelectRsp accountSelectRsp = AccountSelectRsp.builder().build();
        if(req.getChannel()== CHANNEL_ALIPAY){
            account_info = "";
        }
        else if(req.getChannel()== CHANNEL_WXCHAT){
            account_info = "";
        }
        else if(req.getChannel()== CHANNEL_BANK){
            SelectCardReq selectCardReq = SelectCardReq.builder()
                    .amount(req.getAmount()).build();
            result = (ResponseResult) bankCardService.bankSelect(selectCardReq);
            if(result.getCode()==BANK_CARD_SELECT_SUC.getCode()){
                SelectCardRsp data = (SelectCardRsp) result.getData();
                account_info = data.getName().trim()+"#"+data.getCardNo().trim();
                accountSelectRsp.setAccount_id(data.getAccount_id());
            }

        }
        if(StringUtils.isEmpty(account_info)){
            throw new ServiceException(ACCOUNT_SELECT_FAILED);
        }
        //2)生成附言
        accountSelectRsp.setRemark(bankRemarkService.genRemark());

        //3)填充其他信息
        accountSelectRsp.setChannel(req.getChannel());
        accountSelectRsp.setAmount(req.getAmount());
        accountSelectRsp.setAccount_info(account_info.trim());
        result.setData(accountSelectRsp);
        return accountSelectRsp;
    }


    private String getOrderNo(PayDepositReq req){
        String orderNo = redisDao.getOrderNo();
        if(StringUtils.isEmpty(orderNo)){
            throw new ServiceException(GENERATION_ORDER_NO_FAILED);
        }
        return req.getMerchantId()+"@" + orderNo;
    }




}
