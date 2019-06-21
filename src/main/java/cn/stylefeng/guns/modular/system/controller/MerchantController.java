package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Merchant;
import cn.stylefeng.guns.modular.system.service.IMerchantService;

import java.util.UUID;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-06-21 15:47:40
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController {

    private String PREFIX = "/system/merchant/";

    @Autowired
    private IMerchantService merchantService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "merchant.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/merchant_add")
    public String merchantAdd() {
        return PREFIX + "merchant_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/merchant_update/{merchantId}")
    public String merchantUpdate(@PathVariable Integer merchantId, Model model) {
        Merchant merchant = merchantService.selectById(merchantId);
        model.addAttribute("item",merchant);
        LogObjectHolder.me().set(merchant);
        return PREFIX + "merchant_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return merchantService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Merchant merchant) {
        merchant.setPrivateKey(UUID.randomUUID()+"");
        merchant.setPassword(MD5Util.encrypt("123456"));
        merchant.setSettlePwd(MD5Util.encrypt("123456"));
        merchantService.insert(merchant);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer merchantId) {
        merchantService.deleteById(merchantId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Merchant merchant) {
        merchantService.updateById(merchant);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{merchantId}")
    @ResponseBody
    public Object detail(@PathVariable("merchantId") Integer merchantId) {
        return merchantService.selectById(merchantId);
    }
}
