package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Trade;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-13
 */
public interface TradeMapper extends BaseMapper<Trade> {
    Trade selectTradeByOrderNo(String orderNo);
}
