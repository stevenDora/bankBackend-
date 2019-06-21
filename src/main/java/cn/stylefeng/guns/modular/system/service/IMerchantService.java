package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.model.CompanyDo;
import cn.stylefeng.guns.modular.system.model.Merchant;
import cn.stylefeng.guns.modular.system.model.Trade;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-21
 */
public interface IMerchantService extends IService<Merchant> {
    CompanyDo getCompany(Long companyId);
    void saveCompanyCashFlow(Trade trade);

    void tradeNotify(Trade trade);
}
