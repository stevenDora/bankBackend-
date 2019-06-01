package cn.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.stylefeng.guns.modular.system.model.BankFlow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-29
 */
public interface BankFlowMapper extends BaseMapper<BankFlow> {

    @Update("update bank_flow set status = #{status} where status = #{status}-1 and id = #{position}")
    Integer handleBankFlow(@Param("position") Integer position,
                           @Param("status") Byte status);
}
