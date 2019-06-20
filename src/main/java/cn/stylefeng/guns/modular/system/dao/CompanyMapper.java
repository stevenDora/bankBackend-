package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.CompanyDo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

@Repository
public interface CompanyMapper {



    CompanyDo getCompany(@Param("companyId") Long companyId);

    @Update("update company set available_amount = #{availableAmount}," +
            "total_amount = {totalAmount} where id = #{id}")
    Integer updateAccount(CompanyDo company);
}