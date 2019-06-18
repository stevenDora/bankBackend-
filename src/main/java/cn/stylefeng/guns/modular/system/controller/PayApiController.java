package cn.stylefeng.guns.modular.system.controller;


import cn.stylefeng.guns.modular.system.constant.PayApiEnum;
import cn.stylefeng.guns.modular.system.dto.FlowNotifyReq;
import cn.stylefeng.guns.modular.system.dto.PayDepositReq;
import cn.stylefeng.guns.modular.system.dto.ResponseResult;
import cn.stylefeng.guns.modular.system.model.CompanyDo;
import cn.stylefeng.guns.modular.system.service.CompanyService;
import cn.stylefeng.guns.modular.system.service.PayApiService;
import cn.stylefeng.guns.modular.system.utils.SignUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/payApi")
public class PayApiController {


    private String PREFIX = "/system/payApi/";



    @Autowired
    private PayApiService payApi;

    private static Logger logger = LoggerFactory.getLogger(PayApiController.class);

    @Autowired
    private CompanyService companyService;

    @ApiOperation("充值申请")
    @RequestMapping(value = "/deposit")
    @ResponseBody
    public Object deposit(@Valid @RequestBody PayDepositReq req,
                          BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return ResponseResult.builder().code(PayApiEnum.DEPOSIT_FAILED_INPUT_PARAMETERS.getCode())
                    .msg(bindingResult.getFieldError().getDefaultMessage()).build();
        }

        String reqStr = JSONObject.toJSONString(req);
        logger.info("商户->counter充值入参{}", reqStr);
        boolean flag = this.checkSign(reqStr, req.getSign(), req.getIsSign(), req.getMerchantId());


        if (flag == false) {
            return ResponseResult.builder().code(PayApiEnum.DEPOSIT_FAILED_SIGN.getCode())
                    .msg(PayApiEnum.DEPOSIT_FAILED_SIGN.getDesc()).build();
        }

        return payApi.deposit(req);



    }


    @ApiOperation("跳转支付收银台页面")
    @RequestMapping(value = "/counter/{orderNo}")
    @ResponseBody
    public Object counter(@PathVariable String orderNo){
        return PREFIX+"counter.html?orderNo="+orderNo;
    }


    @ApiOperation("获取下单充值结果")
    @RequestMapping(value = "/getDepositResult")
    @ResponseBody
    public Object getDepositResult(@RequestParam String orderNo){
        return payApi.getDepositResult(orderNo);
    }


    @ApiOperation("获取支付详细信息")
    @RequestMapping(value = "/getDepositDetail/{orderNo}")
    @ResponseBody
    public Object getDepositDetail(@RequestParam("orderNo") String orderNo){
        return payApi.getDepositDetail(orderNo);
    }

    @ApiOperation("异步流水到账")
    @RequestMapping(value = "/notify")
    @ResponseBody
    public Object notify(@Valid @RequestBody FlowNotifyReq req,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseResult.builder().code(PayApiEnum.DEPOSIT_FAILED_INPUT_PARAMETERS.getCode())
                    .msg(bindingResult.getFieldError().getDefaultMessage()).build();
        }

        String reqStr = JSONObject.toJSONString(req);
        logger.info("商户->counter充值入参{}", reqStr);
        boolean flag = this.checkCommSign(reqStr, req.getSign(), req.getIsSign());


        if (flag == false) {
            return ResponseResult.builder().code(PayApiEnum.FLOW_FAILED_SIGN.getCode())
                    .msg(PayApiEnum.FLOW_FAILED_SIGN.getDesc()).build();
        }
        return payApi.notify(req);
    }

    private boolean checkSign(String reqStr, String sign, String isSign, Long companyId) {
        if (StringUtils.isNotEmpty(isSign)) {
            return true;
        }
        CompanyDo company = companyService.getCompany(companyId);
        if (company == null) {
            return false;
        }
        return verifySign(reqStr, sign, company.getPrivateKey());
    }

    private boolean checkCommSign(String reqStr, String sign, String isSign) {
        if (StringUtils.isNotEmpty(isSign)) {
            return true;
        }
        return verifySign(reqStr, sign, "0xaabbccddeeffuuoovvsszz");
    }

    private boolean verifySign(String reqStr, String sign, String s) {
        Map<String, Object> reqMap = JSONObject.parseObject(reqStr, Map.class);
        reqMap.remove("sign");
        String curSign = SignUtils.getSign(reqMap, s);
        if (sign.equals(curSign)) {
            return true;
        }
        return false;
    }


}
