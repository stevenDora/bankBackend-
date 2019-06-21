package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dao.CompanyCashFlowMapper;
import cn.stylefeng.guns.modular.system.model.CompanyCashFlow;
import cn.stylefeng.guns.modular.system.model.CompanyDo;
import cn.stylefeng.guns.modular.system.model.Merchant;
import cn.stylefeng.guns.modular.system.dao.MerchantMapper;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.service.IMerchantService;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.SAVE_COMPANY_CASH_FLOW_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-21
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {
    @Autowired
    private MerchantMapper companyMapper;

    @Autowired
    private CompanyCashFlowMapper companyCashFlowMapper;

    @Override
    public CompanyDo getCompany(Long companyId) {
        return companyMapper.getCompany(companyId);
    }

    private static Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Override
    @Transactional
    public void saveCompanyCashFlow(Trade trade) {
        //商户帐变逻辑
        BigDecimal updatedAb = new BigDecimal(0.00);
        BigDecimal totalAmount = new BigDecimal(0.00);
        Integer companyNo = trade.getCompanyNo();
        final CompanyDo company = companyMapper.getCompany(Long.valueOf(companyNo));

        logger.info("company cash flow start..orderNo:{},companyNo:{},company name :{}",
                trade.getOrderNo(), companyNo, company.getCompanyName());

        logger.info("before update companyNo:{},companyName:{} ab:{},fa:{},total:{} ",
                companyNo, company.getCompanyName(),
                company.getAvailableAmount(),
                company.getFreezingAmount(),
                company.getTotalAmount());

        updatedAb = updatedAb.add(company.getAvailableAmount()).add(trade.getActualAmount());
        totalAmount = totalAmount.add(company.getTotalAmount()).add(trade.getActualAmount());
        CompanyCashFlow companyCashFlow = CompanyCashFlow.builder().build();
        companyCashFlow.setCollectionAmount(trade.getActualAmount());
        companyCashFlow.setCompanyId(companyNo);
        companyCashFlow.setCollectionBfs(trade.getServiceFee());
        companyCashFlow.setOldAb(company.getAvailableAmount());
        companyCashFlow.setOldFa(company.getFreezingAmount());
        companyCashFlow.setUpdatedAb(updatedAb);
        companyCashFlow.setOrderNo(trade.getOrderNo());
        companyCashFlow.setCrtTime(new Date());
        companyCashFlow.setUptTime(new Date());
        logger.info("company cash flow begin to insert cash flow " +
                        " orderNo:{},companyNo:{},company name :{} flow is {}",
                trade.getOrderNo(), companyNo, company.getCompanyName(), companyCashFlow.toString());
        companyCashFlowMapper.insert(companyCashFlow);

        company.setAvailableAmount(updatedAb);
        company.setTotalAmount(totalAmount);

        Integer company_row = companyMapper.updateAccount(company);
        if (company_row == 0) {
            throw new ServiceException(SAVE_COMPANY_CASH_FLOW_ERROR);
        }
        logger.info("after update companyNo:{},companyName:{} ab:{},fa:{},total:{} ",
                companyNo, company.getCompanyName(),
                company.getAvailableAmount(),
                company.getFreezingAmount(),
                company.getTotalAmount());

        logger.info("company cash flow end..orderNo:{},companyNo:{},company name :{} flow is {}",
                trade.getOrderNo(), companyNo, company.getCompanyName(), companyCashFlow.toString());
    }
}
