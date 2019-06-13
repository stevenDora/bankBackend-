package cn.stylefeng.guns.modular.system.controller;


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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/payApi")
public class PayApiController {


    @Autowired
    private PayApiService payApi;

    private static Logger logger = LoggerFactory.getLogger(PayApiController.class);

    @Autowired
    private CompanyService companyService;

    @ApiOperation("充值")
    @RequestMapping(value = "/deposit")
    @ResponseBody
    public Object deposit(@Valid @RequestBody PayDepositReq req,
                          BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return ResponseResult.builder().code(-1).msg(bindingResult.getFieldError().getDefaultMessage()).build();
        }

        String reqStr = JSONObject.toJSONString(req);
        logger.info("商户->counter充值入参{}", reqStr);
        boolean flag = this.checkSign(reqStr, req.getSign(), req.getIsSign(), req.getMerchantId());


        if (flag == false) {
            return ResponseResult.builder().code(-2).msg("验签失败").build();
        }

        return payApi.deposit(req);



    }


    private boolean checkSign(String reqStr, String sign, String isSign, Long companyId) {
        if (StringUtils.isNotEmpty(isSign)) {
            return true;
        }
        CompanyDo company = companyService.getCompany(companyId);
        if (company == null) {
            return false;
        }
        Map<String, Object> reqMap = JSONObject.parseObject(reqStr, Map.class);
        reqMap.remove("sign");
        String curSign = SignUtils.getSign(reqMap, company.getPrivateKey());
        if (sign.equals(curSign)) {
            return true;
        }
        return false;
    }

}
