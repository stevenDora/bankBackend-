package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dao.CompanyMapper;
import cn.stylefeng.guns.modular.system.model.CompanyDo;
import cn.stylefeng.guns.modular.system.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public CompanyDo getCompany(Long companyId) {
        return companyMapper.getCompany(companyId);
    }

}
