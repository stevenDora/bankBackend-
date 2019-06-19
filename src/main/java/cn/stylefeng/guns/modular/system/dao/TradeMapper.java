package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.FlowData;
import cn.stylefeng.guns.modular.system.model.Trade;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Update("update trade set order_status = 2 where order_status = 1 and order_no = #{orderNo}")
    Integer matchOrder(String orderNo);

    @Select("select order_no from trade" +
            "where order_status = 1 and remark = #{remark} " +
            "and apply_amount = #{amount} and scalper_id = #{scalperId}")
    Trade findTradeByAmountAndRemark(FlowData flowData);
}
