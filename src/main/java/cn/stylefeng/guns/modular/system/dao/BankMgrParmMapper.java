package cn.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.stylefeng.guns.modular.system.model.BankMgrParm;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-30
 */
public interface BankMgrParmMapper extends BaseMapper<BankMgrParm> {

    @Select("select parm_value from bank_mgr_parm where parm_name = #{parmName}")
    Integer getParmValueByName(String parmName);


    @Update("update bank_mgr_parm set parm_value = #{value} where parm_name = #{parmName}")
    void updateParmValueByName(String parmName, Integer value);
}
