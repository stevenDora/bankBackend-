package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dao.ScalperCashFlowMapper;
import cn.stylefeng.guns.modular.system.model.Scalper;
import cn.stylefeng.guns.modular.system.dao.ScalperMapper;
import cn.stylefeng.guns.modular.system.model.ScalperCashFlow;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.service.IScalperService;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.*;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.SAVE_SCALPER_CASH_FLOW_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
@Service
public class ScalperServiceImpl extends ServiceImpl<ScalperMapper, Scalper> implements IScalperService {

    @Autowired
    private ScalperMapper scalperMapper;

    @Autowired
    private ScalperCashFlowMapper scalperCashFlowMapper;

    private static Logger logger = LoggerFactory.getLogger(ScalperServiceImpl.class);

    @Override
    public Scalper findScalperByScalperId(String scalper_id) {
        return scalperMapper.findScalperByScalperId(scalper_id);
    }

    @Override
    @Transactional
    public void saveScalperCashFlow(Trade trade) {
        //收款人帐变逻辑
        BigDecimal updatedAb = new BigDecimal(0.00);
        BigDecimal updatedFa = new BigDecimal(0.00);
        BigDecimal updateTotalAmount = new BigDecimal(0.00);
        String scalperId = trade.getScalperId();
        Scalper scalper = scalperMapper.findScalperByScalperId(scalperId);

        logger.info("scalper cash flow start..orderNo:{},companyNo:{}",
                trade.getOrderNo(), scalperId);

        logger.info("before update scalperId:{},ab:{},fa:{},total:{} ",
                scalperId,
                scalper.getAvaliableBalance(),
                scalper.getFreezeBalance(),
                scalper.getTotalBalance());

        ScalperCashFlow scalperCashFlow = ScalperCashFlow.builder().build();
        if(trade.getOrderStatus()== ORDER_STATUS_SUCCESS){
            logger.info("handle order success scalper cash flow...");
            updatedFa = scalper.getFreezeBalance().subtract(trade.getActualAmount());
            updateTotalAmount = scalper.getTotalBalance().subtract(trade.getActualAmount());
            scalperCashFlow.setUpdatedAb(scalper.getAvaliableBalance());
            scalperCashFlow.setUpdatedFa(updatedFa);
            scalperCashFlow.setCollectionAmount(trade.getActualAmount());
            scalperCashFlow.setTransactionTime(trade.getArriveTime());
        }
        else if(trade.getOrderStatus()== ORDER_STATUS_OVERDUE){
            logger.info("handle order overDue scalper cash flow...");
            updatedAb = updatedAb.add(scalper.getAvaliableBalance()).add(trade.getApplyAmount());
            updatedFa = updatedFa.add(scalper.getFreezeBalance()).subtract(trade.getApplyAmount());
            scalperCashFlow.setUpdatedAb(updatedAb);
            scalperCashFlow.setUpdatedFa(updatedFa);
            scalperCashFlow.setCollectionAmount(trade.getApplyAmount());
            scalperCashFlow.setTransactionTime(null);
        }
        else if(trade.getOrderStatus()== ORDER_STATUS_PROCESS){
            logger.info("handle order process scalper cash flow...");
            updatedAb = updatedAb.add(scalper.getAvaliableBalance()).subtract(trade.getApplyAmount());
            updatedFa = updatedFa.add(scalper.getFreezeBalance()).add(trade.getApplyAmount());
            scalperCashFlow.setCollectionAmount(trade.getApplyAmount());
            scalperCashFlow.setUpdatedAb(updatedAb);
            scalperCashFlow.setUpdatedFa(updatedFa);
            scalperCashFlow.setTransactionTime(null);
        }
        else {
            throw new ServiceException(SAVE_SCALPER_CASH_FLOW_ERROR);
        }
        scalperCashFlow.setScalperId(scalperId);
        scalperCashFlow.setOldAb(scalper.getAvaliableBalance());
        scalperCashFlow.setOldFa(scalper.getFreezeBalance());
        scalperCashFlow.setOrderNo(trade.getOrderNo());
        scalperCashFlow.setOrderStatus(trade.getOrderStatus());
        scalperCashFlow.setUptTime(new Date());
        scalperCashFlow.setCrtTime(new Date());

        logger.info("scalper cash flow begin to insert cash flow " +
                        " orderNo:{},scalperId:{},flow is {}",
                trade.getOrderNo(), scalperId, scalperCashFlow.toString());
        scalperCashFlowMapper.insert(scalperCashFlow);

        scalper.setAvaliableBalance(updatedAb);
        scalper.setFreezeBalance(updatedFa);
        scalper.setTotalBalance(updateTotalAmount);

        Integer scalper_row = scalperMapper.updateAccount(scalper);

        if (scalper_row == 0) {
            throw new ServiceException(SAVE_SCALPER_CASH_FLOW_ERROR);
        }

        logger.info("after update scalperId:{},ab:{},fa:{},total:{} ",
                scalperId,scalper.getAvaliableBalance(),
                scalper.getFreezeBalance(),
                scalper.getTotalBalance());

        logger.info("scalper cash flow end..orderNo:{},scalper_id:{} flow is {}",
                trade.getOrderNo(), scalperId, scalperCashFlow.toString());

    }

    @Override
    public String findBestScalper(Integer channel, BigDecimal amount) {
        return scalperMapper.findBestScalper(channel,amount);
    }

}
