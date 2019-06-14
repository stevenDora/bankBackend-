package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.constant.PayApiEnum;
import cn.stylefeng.guns.modular.system.dto.PayDepositReq;
import cn.stylefeng.guns.modular.system.dto.PayDepositRsp;
import cn.stylefeng.guns.modular.system.dto.ResponseResult;
import cn.stylefeng.guns.modular.system.dto.SelectCardReq;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.ITradeService;
import cn.stylefeng.guns.modular.system.service.PayApiService;
import cn.stylefeng.guns.modular.system.utils.DateUtil;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.ORDER_STATUS_PROCESS;
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


    @Override
    public Object deposit(PayDepositReq req) {

        ResponseResult result = null;
        String payLink = PAYLINK_PREFIX;

        result = (ResponseResult) this.accountSelect(req);
        if(result.getCode()!=BANK_CARD_SELECT_SUC.getCode()){
            logger.info("派单失败!! 请求信息："+ req.toString());
            return result;
        }
        //生成订单id
        String orderNo = getOrderNo(req);
        //异步生成订单

        this.createOrder(req,orderNo);
        payLink  = payLink + orderNo;
        PayDepositRsp depositRsp = PayDepositRsp.builder()
                .amount(req.getAmount())
                .merchant_id(req.getMerchantId())
                .channel(req.getChannel())
                .merchant_order_no(req.getMerchantOrderNo())
                .payUrl(payLink)
                .platform_order_no(orderNo)
                .invalidDate(DateUtil.addMinute(new Date(), 10))
                .build();

        return ResponseResult.builder()
                .data(depositRsp)
                .code(PayApiEnum.DEPOSIT_APPLY_SUCCESS.getCode())
                .msg(PayApiEnum.DEPOSIT_APPLY_SUCCESS.getDesc()).build();
    }


    @Async
    @Transactional
    @Override
    public void createOrder(PayDepositReq req,String orderNo){
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
                .serviceFee(new BigDecimal(0))
                .orderStatus(ORDER_STATUS_PROCESS)
                .pushStatus(PUSH_STATUS_UNHANDLE).build();
        tradeService.insert(trade);
    }

    @Override
    public Object getDepositDetail(String orderNo) {
        return null;
    }


    private Object accountSelect(PayDepositReq req){
        ResponseResult result = null;

        if(req.getChannel()== CHANNEL_ALIPAY){

        }
        else if(req.getChannel()== CHANNEL_WXCHAT){

        }
        if(req.getChannel()== CHANNEL_BANK){
            SelectCardReq selectCardReq = SelectCardReq.builder()
                    .amount(req.getAmount()).build();
            result = (ResponseResult) bankCardService.bankSelect(selectCardReq);
        }

        return result;
    }


    private String getOrderNo(PayDepositReq req){
        String orderNo = redisDao.getOrderNo();
        if(StringUtils.isEmpty(orderNo)){
            throw new ServiceException(GENERATION_ORDER_NO_FAILED);
        }
        return req.getMerchantId()+"@" + orderNo;
    }


}
