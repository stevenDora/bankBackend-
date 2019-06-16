package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dto.AccountSelectRsp;
import cn.stylefeng.guns.modular.system.dto.ResponseResult;
import cn.stylefeng.guns.modular.system.dto.SelectCardReq;
import cn.stylefeng.guns.modular.system.dto.SelectCardRsp;
import cn.stylefeng.guns.modular.system.service.AssignOrderService;
import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.IBankRemarkService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_ALIPAY;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_BANK;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_WXCHAT;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.ACCOUNT_SELECT_FAILED;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.BANK_CARD_SELECT_SUC;

public class AssignOrderServiceImpl implements AssignOrderService {

    @Autowired
    private IBankCardService bankCardService;

    @Autowired
    private IBankRemarkService bankRemarkService;

    @Override
    public AccountSelectRsp accountSelect(Integer channel, BigDecimal amount){
        ResponseResult result = null;
        String account_info = "";
        AccountSelectRsp accountSelectRsp = AccountSelectRsp.builder().build();
        if(channel == CHANNEL_ALIPAY){
            account_info = "";
        }
        else if(channel == CHANNEL_WXCHAT){
            account_info = "";
        }
        else if(channel == CHANNEL_BANK){
            SelectCardReq selectCardReq = SelectCardReq.builder()
                    .amount(amount).build();
            result = (ResponseResult) bankCardService.bankSelect(selectCardReq);
            if(result.getCode()==BANK_CARD_SELECT_SUC.getCode()){
                SelectCardRsp data = (SelectCardRsp) result.getData();
                account_info = data.getName().trim()+"#"+data.getCardNo().trim();
                accountSelectRsp.setAccount_id(data.getAccount_id());
            }

        }
        if(StringUtils.isEmpty(account_info)){
            throw new ServiceException(ACCOUNT_SELECT_FAILED);
        }
        //2)生成附言
        accountSelectRsp.setRemark(bankRemarkService.genRemark());

        //3)填充其他信息
        accountSelectRsp.setChannel(channel);
        accountSelectRsp.setAmount(amount);
        accountSelectRsp.setAccount_info(account_info.trim());
        result.setData(accountSelectRsp);
        return accountSelectRsp;
    }

}
