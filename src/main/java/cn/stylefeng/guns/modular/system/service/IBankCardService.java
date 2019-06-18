package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.dto.ResponseResult;
import com.baomidou.mybatisplus.service.IService;
import cn.stylefeng.guns.modular.system.dto.BankCashflowVo;
import cn.stylefeng.guns.modular.system.dto.BankFlowsModel;
import cn.stylefeng.guns.modular.system.dto.SelectCardReq;
import cn.stylefeng.guns.modular.system.model.BankCard;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author steven123
 * @since 2019-05-27
 */
public interface IBankCardService extends IService<BankCard> {

    void changeSwitch(Integer id, Byte status);
    void updateOnlineStats(Integer card_id, Byte status);
    void syncBankOnlineStatus(Integer card_id);
    void reportBankOnlineStatus(String cardNo);
    void syncAllBankOnlineStatus();
    Object bankSelect(SelectCardReq req);

    Object saveBankCashFlow(BankCashflowVo req, Date cutoffTime);

    void saveFlows(BankFlowsModel req);

    ResponseResult getTransLastTimeByCardNo(String card_no);

    Map getBankCard(SelectCardReq selectCardReq);
}
