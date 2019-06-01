package cn.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.stylefeng.guns.modular.system.model.BankFlow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-29
 */
public interface BankFlowMapper extends BaseMapper<BankFlow> {

    @Update("update bank_flow set status = #{status} where id = #{position}")
    Integer handleBankFlow(@Param("position") Integer position,
                           @Param("status") Byte status);


    @Select("select id,card_no,amount,avaliable_balance,peer_card_no " +
            " transaction_time,remark,status from bank_flow where status = #{status}")
    List<BankFlow> getFlowsByStatus(@Param("status") Byte status);

    @Update("update bank_flow set status = #{bankFlowDone} where status = 1")
    List<BankFlow> handleBankFlows(Byte bankFlowDone);
}
