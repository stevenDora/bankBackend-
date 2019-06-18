package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dao.ScalperMapper;
import cn.stylefeng.guns.modular.system.dto.AccountSelectRsp;
import cn.stylefeng.guns.modular.system.dto.ResponseResult;
import cn.stylefeng.guns.modular.system.dto.SelectCardReq;
import cn.stylefeng.guns.modular.system.dto.SelectCardRsp;
import cn.stylefeng.guns.modular.system.service.AssignOrderService;
import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.IBankRemarkService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_ALIPAY;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_BANK;
import static cn.stylefeng.guns.modular.system.constant.Constant.Channel.CHANNEL_WXCHAT;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.ACCOUNT_SELECT_FAILED;
import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.BANK_CARD_SELECT_SUC;

@Service
public class AssignOrderServiceImpl implements AssignOrderService {


    private static Logger logger = LoggerFactory.getLogger(AssignOrderServiceImpl.class);

    @Autowired
    private ScalperMapper scalperMapper;

    @Autowired
    private IBankCardService bankCardService;

    @Autowired
    private IBankRemarkService remarkService;

    @Transactional
    @Override
    public AccountSelectRsp accountSelect(String orderNo,Integer channel, BigDecimal amount){
        Integer try_times = 3;
        ResponseResult result = null;
        String account_info = "";
        AccountSelectRsp accountSelectRsp = AccountSelectRsp.builder().build();
        accountSelectRsp.setIsSuc(false);
        accountSelectRsp.setAccount_info("");
        accountSelectRsp.setAccount_id(null);
        accountSelectRsp.setOrderNo(orderNo);
        while (try_times-->3){
            String scalper_id = findBestScalper(channel,amount);
            if(StringUtils.isEmpty(scalper_id)){
                logger.info("select scalper failed,nobody find channel:{},amount:{},orderNo{} !!!",
                        channel,amount,orderNo);
                throw new ServiceException(ACCOUNT_SELECT_FAILED);
            }
            //查询账户信息
            if(channel == CHANNEL_ALIPAY){
                account_info = "";
            }
            else if(channel == CHANNEL_WXCHAT){
                account_info = "";
            }
            else if(channel == CHANNEL_BANK){
                SelectCardReq selectCardReq = SelectCardReq.builder()
                        .amount(amount)
                        .scalper_id(scalper_id).build();
                Map<String,String> bankCard = bankCardService.getBankCard(selectCardReq);
                if(StringUtils.isEmpty(bankCard)){
                    accountSelectRsp.setAccount_info("");
                }
                else {
                    SelectCardRsp data = (SelectCardRsp) result.getData();
                    accountSelectRsp.setAccount_id(data.getAccount_id());
                    accountSelectRsp.setAccount_info(data.getName().trim()+"#"+data.getCardNo().trim());
                    if(StringUtils.isEmpty(accountSelectRsp.getAccount_info())){
                        throw new ServiceException(ACCOUNT_SELECT_FAILED);
                    }
                }
            }

            //检查是否找到账号，如果找到了再扣款
            if(StringUtils.isEmpty(accountSelectRsp.getAccount_info())){
                accountSelectRsp.setIsSuc(false);
            }
            else {
                if(updateScalperAccount(scalper_id, amount) > 0){
                    accountSelectRsp.setIsSuc(true);
                }
                else {
                    logger.info("收款人余额不够，可能是线程并发造成的！！！,继续循环尝试.try_times:{}",try_times);
                    accountSelectRsp.setIsSuc(false);
                }

            }
            if(accountSelectRsp.getIsSuc()){
                break;
            }
            //循环
            logger.info("select for next scalper orderNo:{}",orderNo);
            accountSelectRsp.setIsSuc(false);
            accountSelectRsp.setAccount_info("");
            accountSelectRsp.setAccount_id(null);
        }

        //2)生成附言
        accountSelectRsp.setRemark(remarkService.genRemark());

        //3)填充其他信息
        accountSelectRsp.setChannel(channel);
        accountSelectRsp.setAmount(amount);
        accountSelectRsp.setAccount_info(account_info.trim());
        result.setData(accountSelectRsp);
        return accountSelectRsp;
    }

    private Integer updateScalperAccount(String scalper_id, BigDecimal amount) {
        return scalperMapper.updateScalperAccount(scalper_id,amount);
    }

    private String findBestScalper(Integer channel, BigDecimal amount) {
        return scalperMapper.findBestScalper(channel,amount);
    }


}
