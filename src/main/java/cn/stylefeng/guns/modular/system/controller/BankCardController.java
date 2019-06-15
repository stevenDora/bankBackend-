package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.modular.system.annotate.AccessLimit;
import cn.stylefeng.guns.modular.system.dto.BankFlowsModel;
import cn.stylefeng.guns.modular.system.dto.SelectCardReq;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.BankCard;
import cn.stylefeng.guns.modular.system.service.IBankCardService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.stylefeng.guns.modular.system.constant.PayApiEnum.*;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-06-01 18:25:18
 */
@Controller
@RequestMapping("/bankCard")
public class BankCardController extends BaseController {

    private String PREFIX = "/system/bankCard/";

    @Autowired
    private IBankCardService bankCardService;

    private static Logger logger = LoggerFactory.getLogger(BankCardController.class);

    /**
     * 跳转到银行卡管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bankCard.html";
    }

    /**
     * 跳转到添加银行卡管理
     */
    @RequestMapping("/bankCard_add")
    public String bankCardAdd() {
        return PREFIX + "bankCard_add.html";
    }

    /**
     * 跳转到修改银行卡管理
     */
    @RequestMapping("/bankCard_update/{bankCardId}")
    public String bankCardUpdate(@PathVariable Integer bankCardId, Model model) {
        BankCard bankCard = bankCardService.selectById(bankCardId);
        model.addAttribute("item",bankCard);
        LogObjectHolder.me().set(bankCard);
        return PREFIX + "bankCard_edit.html";
    }

    /**
     * 获取银行卡管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return bankCardService.selectList(null);
    }

    /**
     * 新增银行卡管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BankCard bankCard) {
        bankCardService.insert(bankCard);
        return SUCCESS_TIP;
    }

    /**
     * 银行卡状态变化
     */
    @RequestMapping(value = "/changeSwitch")
    @ResponseBody
    public Object changeSwitch(Integer id, Byte status,
                               HttpServletResponse response) throws IOException {
        bankCardService.changeSwitch(id,status);
        /*logger.info("create image start");

        QrImage para = new QrImage.Builder()
                .setFileOutputPath("C:\\work\\code\\" + 1 + ".jpg")
                .setQrContent("http://www.baidu.com?a=" + "123")
                .setQrHeight(300)
                .setQrWidth(300)
                .setTopWrodHeight(100)
                .setWordContent("test" + 1)
                .setQrIconFilePath("C:\\work\\code\\" + "qrTemplate.png")
                .setWordSize(18).build();
        ImgQrTool.createWebQrWithFontsAbove(para,response.getOutputStream());
        logger.info("create image finished");*/
        return SUCCESS_TIP;
    }


    /**
     * 删除银行卡管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer bankCardId) {
        bankCardService.deleteById(bankCardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改银行卡管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BankCard bankCard) {
        bankCardService.updateById(bankCard);
        return SUCCESS_TIP;
    }

    /**
     * 银行卡管理详情
     */
    @RequestMapping(value = "/detail/{bankCardId}")
    @ResponseBody
    public Object detail(@PathVariable("bankCardId") Integer bankCardId) {
        return bankCardService.selectById(bankCardId);
    }

    /**
     * 银行卡心跳上报
     */
    @BussinessLog(value = "心跳消息處理")
    @ApiOperation("心跳")
    @RequestMapping(value = "/heartBeat",method= RequestMethod.POST)
    @ResponseBody
    public Object reportBankOnlineStatus(@RequestParam String cardNo) {
        if(StringUtils.isEmpty(cardNo)){
            throw new ServiceException(BANK_CARD_HEART_BEAT_FAILED);
        }
        bankCardService.reportBankOnlineStatus(cardNo);
        return SUCCESS_TIP;
    }

    /**
     * 银行卡选取
     */
    @BussinessLog(value = "銀行卡選取")
    @RequestMapping(value = "/bankSelect")
    @ResponseBody
    public Object bankSelect(@RequestBody SelectCardReq req) {
        if(StringUtils.isEmpty(req)||StringUtils.isEmpty(req.getAmount())){
            throw new ServiceException(BANK_CARD_SELECT_FAILED);
        }
        return bankCardService.bankSelect(req);
    }


    @BussinessLog(value = "解析流水日誌")
    @ApiOperation("銀行流水推送")
    @RequestMapping(value = "/pushBankCashflow",method= RequestMethod.POST)
    @ResponseBody
    public Object pushBankCashflow(@RequestBody BankFlowsModel req) {
        if(StringUtils.isEmpty(req)
                || StringUtils.isEmpty(req.getBankCashflowVos())){
            throw new ServiceException(BANK_CARD_RECV_FAILED_NULL);

        }
        bankCardService.saveFlows(req);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "獲取最後一次交易時間日誌")
    @ApiOperation("獲取指定卡最後一次交易時間")
    @RequestMapping(value = "/getTransLastTime",method= RequestMethod.GET)
    @ResponseBody
    public Object getTransLastTime(@RequestParam String card_no) {
        if(StringUtils.isEmpty(card_no)){
            throw new ServiceException(BANK_CARD_RECV_FAILED_CARD_NO_INVALID);
        }
        return bankCardService.getTransLastTimeByCardNo(card_no);
    }
}
