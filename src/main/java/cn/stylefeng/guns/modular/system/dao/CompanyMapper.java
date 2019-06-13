package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.CompanyDo;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

@Repository
public interface CompanyMapper {
    CompanyDo getCompany(@Param("companyId") Long companyId);

}