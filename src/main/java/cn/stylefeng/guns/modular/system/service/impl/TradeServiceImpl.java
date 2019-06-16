package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.dto.AccountSelectRsp;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.dao.TradeMapper;
import cn.stylefeng.guns.modular.system.service.AssignOrderService;
import cn.stylefeng.guns.modular.system.service.ITradeService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.stylefeng.guns.modular.system.constant.Constant.OrderStatus.ORDER_STATUS_PROCESS;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-13
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements ITradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private AssignOrderService assignOrderService;

    @Override
    public Trade selectTradeByOrderNo(String orderNo) {
        if(StringUtils.isEmpty(orderNo)){
            return null;
        }
        return tradeMapper.selectTradeByOrderNo(orderNo);
    }

    @Override
    @Transactional
    public void createOrder(String orderNo) {
        String orderStr = redisDao.get(orderNo);
        Trade trade = JSONObject.parseObject(orderStr, Trade.class);
        AccountSelectRsp rsp = assignOrderService.accountSelect(trade.getChannel(),
                trade.getApplyAmount());

        trade.setRemark(rsp.getRemark());
        trade.setAccountId(rsp.getAccount_id());
        trade.setAccountInfo(rsp.getAccount_info());
        trade.setOrderStatus(ORDER_STATUS_PROCESS);
        tradeMapper.insert(trade);
    }

}
