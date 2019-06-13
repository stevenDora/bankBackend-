package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.constant.Constant;
import cn.stylefeng.guns.modular.system.dao.BankCardMapper;
import cn.stylefeng.guns.modular.system.dao.BankFlowMapper;
import cn.stylefeng.guns.modular.system.dto.*;
import cn.stylefeng.guns.modular.system.model.BankCard;
import cn.stylefeng.guns.modular.system.model.BankFlow;
import cn.stylefeng.guns.modular.system.service.IBankCardService;
import cn.stylefeng.guns.modular.system.service.IBankRemarkService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.guns.modular.system.constant.BankManageEnum.*;
import static cn.stylefeng.guns.modular.system.constant.Constant.*;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author steven123
 * @since 2019-05-27
 */
@Service
public class BankCardServiceImpl extends ServiceImpl<BankCardMapper, BankCard> implements IBankCardService {

    private static Logger logger = LoggerFactory.getLogger(BankCardServiceImpl.class);

    @Resource
    private BankCardMapper bankCardMapper;

    @Resource
    private BankFlowMapper bankFlowMapper;

    @Resource
    private IBankRemarkService bankRemarkService;

    @Resource
    private RedisDao redisDao;

    @Override
    public void changeSwitch(Integer id,Byte status) {
        bankCardMapper.changeSwitch(id,status);
    }

    @Override
    public void updateOnlineStats(Integer card_id,Byte status) {
        bankCardMapper.updateOnlineStats(card_id,status);
    }

    public List<Integer> getAllCardIds() {
        return bankCardMapper.getAllCardIds();
    }


    @Override
    @Transactional
    public void syncAllBankOnlineStatus(){
        List<Integer> allCardIds = getAllCardIds();
        for (Integer card_id:
                allCardIds) {
            syncBankOnlineStatus(card_id);
        }
    }

    @Override
    @Transactional
    public Object bankSelect(SelectCardReq req) {
        //1)轮训选择银行卡
        //匹配条件
        //1.时间;2.收款上限;3.开关
        BankCard matchedCard = null;
        List<BankCard> bankCards = bankCardMapper.matchCards(req.getAmount());
        for (BankCard card:bankCards) {
            int affectRows = bankCardMapper.updateCardData(card.getId(), req.getAmount());
            if(affectRows == 1){
                matchedCard = card;
                logger.info("bankSelect suc!!! cardInfo - > "+matchedCard.toString());
                break;
            }
        }

        if(null == matchedCard){
            //选卡失败,无可用的卡
            ResponseResult errorTip = new ResponseResult(400, "");
            errorTip.setCode(BANK_CARD_SELECT_FAILED_NOT_ENOUGH.getCode());
            errorTip.setMsg(BANK_CARD_SELECT_FAILED_NOT_ENOUGH.getDesc());
            return errorTip;
        }
        //2)生成附言
        String remark = bankRemarkService.genRemark();

        SelectCardRsp rsp = SelectCardRsp.builder()
                .cardNo(matchedCard.getCardNo())
                .bankCode(matchedCard.getBankCode())
                .amount(req.getAmount())
                .address(matchedCard.getAddress())
                .name(matchedCard.getName())
                .remark(remark).build();

        return ResponseResult.builder()
                .data(rsp).code(BANK_CARD_SELECT_SUC.getCode())
                .msg(BANK_CARD_SELECT_SUC.getDesc()).build();
    }

    @Override
    @Transactional
    public Object saveBankCashFlow(BankCashflowVo cashflowVo,Date cutOffTime) {
        BankFlow curCashflow = null;
        try {
            ResponseResult tip = checkCashFlow(cashflowVo,cutOffTime);
            if(tip instanceof ResponseResult){
                logger.info("cashflowVo = {} is illegal!!!info is {}",cashflowVo.toString(),
                        ((ResponseResult)tip).toString());
                return tip;
            }
            curCashflow = BankFlow.builder()
                    .cardNo(cashflowVo.getCardNo().trim())
                    .amount(cashflowVo.getAmount())
                    .avaliableBalance(cashflowVo.getAvaliable_balance())
                    .peerCardNo(cashflowVo.getPeer_card_no().trim())
                    .remark(cashflowVo.getRemark().trim())
                    .transactionTime(cashflowVo.getTransaction_time())
                    .status(BANK_FLOW_PENGDING)
                    .build();
            bankFlowMapper.insert(curCashflow);

            bankCardMapper.updateLastTransactionTime(curCashflow.getCardNo(),
                    curCashflow.getTransactionTime());

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseResult(200,"") ;
    }

    @BussinessLog(value = "校验流水日誌")
    private ResponseResult checkCashFlow(BankCashflowVo cashflowVo, Date cutOffTime){
        if(StringUtils.isEmpty(cashflowVo)){
            ResponseResult errorTip = new ResponseResult(400, "");
            errorTip.setCode(BANK_CARD_RECV_FAILED_NULL.getCode());
            errorTip.setMsg(BANK_CARD_RECV_FAILED_NULL.getDesc());
            return errorTip;
        }
        else if(StringUtils.isEmpty(cashflowVo.getAmount())){
            ResponseResult errorTip = new ResponseResult(400, "");
            errorTip.setCode(BANK_CARD_RECV_FAILED_AMOUNT_INVALID.getCode());
            errorTip.setMsg(BANK_CARD_RECV_FAILED_AMOUNT_INVALID.getDesc());
            return errorTip;
        }
        else if(StringUtils.isEmpty(cashflowVo.getCardNo())){
            ResponseResult errorTip = new ResponseResult(400, "");
            errorTip.setCode(BANK_CARD_RECV_FAILED_CARD_NO_INVALID.getCode());
            errorTip.setMsg(BANK_CARD_RECV_FAILED_CARD_NO_INVALID.getDesc());
            return errorTip;
        }
        else if(StringUtils.isEmpty(cashflowVo.getAvaliable_balance())){
            ResponseResult errorTip = new ResponseResult(400, "");
            errorTip.setCode(BANK_CARD_RECV_FAILED_AB_INVALID.getCode());
            errorTip.setMsg(BANK_CARD_RECV_FAILED_AB_INVALID.getDesc());
            return errorTip;
        }
        else if(StringUtils.isEmpty(cashflowVo.getPeer_card_no())){
            ResponseResult errorTip = new ResponseResult(400, "");
            errorTip.setCode(BANK_CARD_RECV_FAILED_PEER_CARD_NO_INVALID.getCode());
            errorTip.setMsg(BANK_CARD_RECV_FAILED_PEER_CARD_NO_INVALID.getDesc());
            return errorTip;
        }
        else if(StringUtils.isEmpty(cashflowVo.getTransaction_time())){
            ResponseResult errorTip = new ResponseResult(400, "");
            errorTip.setCode(BANK_CARD_RECV_FAILED_TRANSACTION_TIME_INVALID.getCode());
            errorTip.setMsg(BANK_CARD_RECV_FAILED_TRANSACTION_TIME_INVALID.getDesc());
            return errorTip;
        }
        else if(cutOffTime.compareTo(cashflowVo.getTransaction_time()) > 0){
            ResponseResult errorTip = new ResponseResult(400, "");
            errorTip.setCode(BANK_CARD_RECV_FAILED_OLD_DATA.getCode());
            errorTip.setMsg(BANK_CARD_RECV_FAILED_OLD_DATA.getDesc());
            return errorTip;
        }

        return new ResponseResult(200,"");
    }


    @Override
    public void saveFlows(BankFlowsModel req) {
        try {
            String cardNo = req.getBankCashflowVos().get(0).getCardNo();
            if(null==bankCardMapper.getCardIdByCardNo(cardNo)){
                throw new ServiceException(BANK_CARD_RECV_FAILED_CARD_NO_INVALID);
            }
            Map<String, Object> transLastTimeByCardNo = bankCardMapper.getTransLastTimeByCardNo(cardNo);
            Date cutOff_time = (Date) transLastTimeByCardNo.get("last_transaction_time");
            req.sort();
            List<BankCashflowVo> bankCashflowVos = req.getBankCashflowVos();
            logger.info("bankCashflowVos:"+bankCashflowVos.toString());
            for (BankCashflowVo flow:
                    bankCashflowVos) {
                saveBankCashFlow(flow,cutOff_time);
            }
        }catch (Exception e){
            throw new ServiceException(BANK_CARD_RECV_SAVE_FAILED);
        }

    }

    @Override
    public ResponseResult getTransLastTimeByCardNo(String card_no) {

        Map<String, Object> transLastTimeByCardNo = bankCardMapper.getTransLastTimeByCardNo(card_no);

        if(StringUtils.isEmpty(transLastTimeByCardNo)){
            return ResponseResult.builder()
                    .data(null).code(BANK_CARD_QUERY_FAILED_CARDNO_INVALID.getCode())
                    .msg(BANK_CARD_QUERY_FAILED_CARDNO_INVALID.getDesc()).build();
        }
        return ResponseResult.builder()
                .data(transLastTimeByCardNo).code(BANK_CARD_QUERY_SUC.getCode())
                .msg(BANK_CARD_QUERY_SUC.getDesc()).build();
    }


    @Override
    @Transactional
    public void syncBankOnlineStatus(Integer card_id) {

        String key = Constant.BANK_ONLINE_STATUS_PREFIX + card_id + "";
        String bankOnlineStatus = redisDao.get(key);
        if(StringUtils.isEmpty(bankOnlineStatus)){
            // 离线
            updateOnlineStats(card_id, Constant.BANK_ONLINE_STATUS_OFF);
        }
    }

    @Override
    @Transactional
    public void reportBankOnlineStatus(String cardNo) {

        //根据cardNo -> card_id
        Integer cardId = bankCardMapper.getCardIdByCardNo(cardNo);
        if(StringUtils.isEmpty(cardId)){
            throw new ServiceException(BANK_CARD_HEART_BEAT_FAILED);
        }
        String key = Constant.BANK_ONLINE_STATUS_PREFIX+cardId+"";
        redisDao.set(key,BANK_ONLINE_STATUS_ON+"");
        redisDao.expire(key,(long)BANK_ONLINE_STATUS_TIMEOUT_SECONDS);
        updateOnlineStats(cardId, BANK_ONLINE_STATUS_ON);
    }


}

