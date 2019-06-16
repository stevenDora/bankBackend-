package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.dao.TradeMapper;
import cn.stylefeng.guns.modular.system.service.ITradeService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Trade selectTradeByOrderNo(String orderNo) {
        if(StringUtils.isEmpty(orderNo)){
            return null;
        }
        return tradeMapper.selectTradeByOrderNo(orderNo);
    }
}
