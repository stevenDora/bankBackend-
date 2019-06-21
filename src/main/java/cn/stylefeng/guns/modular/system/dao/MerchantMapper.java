package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.CompanyDo;
import cn.stylefeng.guns.modular.system.model.Merchant;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-21
 */
public interface MerchantMapper extends BaseMapper<Merchant> {

    CompanyDo getCompany(@Param("companyId") Long companyId);

    @Update("update company set available_amount = #{availableAmount}," +
            "total_amount = {totalAmount} where id = #{id}")
    Integer updateAccount(CompanyDo company);
}
