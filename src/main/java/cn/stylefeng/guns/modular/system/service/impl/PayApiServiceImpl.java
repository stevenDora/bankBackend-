package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.dto.PayDepositReq;
import cn.stylefeng.guns.modular.system.dto.ResponseResult;
import cn.stylefeng.guns.modular.system.dto.SelectCardReq;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.ITradeService;
import cn.stylefeng.guns.modular.system.service.PayApiService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static cn.stylefeng.guns.modular.system.constant.BankManageEnum.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.*;


@Service
public class PayApiServiceImpl implements PayApiService {

    private static Logger logger = LoggerFactory.getLogger(PayApiServiceImpl.class);


    @Autowired
    private IBankCardService bankCardService;

    @Autowired
    private ITradeService tradeService;

    @Autowired
    private RedisDao redisDao;


    @Override
    public Object deposit(PayDepositReq req) {

        ResponseResult result = null;

        result = (ResponseResult) this.accountSelect(req);
        if(result.getCode()!=BANK_CARD_SELECT_SUC.getCode()){
            logger.info("派单失败!! 请求信息："+ req.toString());
            return result;
        }
        //生成订单id
        String orderNo = getOrderNo(req);
        //异步生成订单
        Trade trade = Trade.builder().build();
        this.createOrder(trade);
        return result;
    }


    @Async
    @Transactional
    @Override
    public void createOrder(Trade trade){
        tradeService.insert(trade);
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
