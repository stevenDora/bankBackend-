package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.FlowData;
import cn.stylefeng.guns.modular.system.model.Trade;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    @Update("update trade set order_status = #{status},account_change = 1 " +
            "where order_status = 1 and order_no = #{orderNo} and account_change = 0")
    Integer matchOrder(String orderNo,Integer status);

    @Select("select order_no from trade" +
            "where order_status = 1 and remark = #{remark} " +
            "and apply_amount = #{amount} and scalper_id = #{scalperId} and account_change = 0")
    @ResultType(cn.stylefeng.guns.modular.system.model.Trade.class)
    Trade findTradeByAmountAndRemark(FlowData flowData);



    @Select("select order_no,scalper_id from t_dora_trade where order_status = 1 " +
            "overDue_time < NOW() limit #{num}")
    @ResultType(cn.stylefeng.guns.modular.system.model.Trade.class)
    List<Trade> findTradeInfoWithInvalidOverTime(Integer num);


    @Select("select order_no,order_status from t_dora_trade where order_status in (2,3) " +
            "and account_change = 1 limit #{num}")
    @ResultType(cn.stylefeng.guns.modular.system.model.Trade.class)
    List<Trade> findNeedAcountChangeOrders(Integer num);

    @Update("update t_dora_trade set account_change = 2 where order_no = #{orderNo} " +
            "and account_change = 1 and order_status in (2,3)")
    Integer execAccountChange(String orderNo);
}
