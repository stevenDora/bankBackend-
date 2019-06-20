package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Scalper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
public interface ScalperMapper extends BaseMapper<Scalper> {

    String findBestScalper(Integer channel, BigDecimal amount);

    Integer updateScalperAccount(String scalper_id, BigDecimal amount);

    @Select("select id,scalper_id,avaliable_balance,freeze_balance," +
            "total_balance,collect_balance_sum_day,collect_balance_max_day," +
            "last_assign_task_time,last_account_time,last_charge_time,last_flow_push_time,alipay_switch" +
            "wx_switch,bank_switch,block_status,alipay_rate,wx_rate,bank_rate from scalper where scalper_id=#{scalper_id}")
    Scalper findScalperByScalperId(String scalper_id);


    @Update("update scalper set avaliable_balance = #{avaliableBalance}" +
            ",freeze_balance = #{freezeBalance},total_balance = #{totalBalance} " +
            " where scalper_id = #{scalperId}")
    Integer updateAccount(Scalper scalper);
}
