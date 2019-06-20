package cn.stylefeng.guns.modular.system.service;


import cn.stylefeng.guns.modular.system.model.CompanyDo;
import cn.stylefeng.guns.modular.system.model.Trade;

public interface CompanyService {
    CompanyDo getCompany(Long companyId);
    void saveCompanyCashFlow(Trade trade);
}
