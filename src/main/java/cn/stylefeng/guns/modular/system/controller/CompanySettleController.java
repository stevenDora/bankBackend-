package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.CompanySettle;
import cn.stylefeng.guns.modular.system.service.ICompanySettleService;

/**
 * 商户结算控制器
 *
 * @author fengshuonan
 * @Date 2019-06-21 17:35:34
 */
@Controller
@RequestMapping("/companySettle")
public class CompanySettleController extends BaseController {

    private String PREFIX = "/system/companySettle/";

    @Autowired
    private ICompanySettleService companySettleService;

    /**
     * 跳转到商户结算首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "companySettle.html";
    }

    /**
     * 跳转到添加商户结算
     */
    @RequestMapping("/companySettle_add")
    public String companySettleAdd() {
        return PREFIX + "companySettle_add.html";
    }

    /**
     * 跳转到修改商户结算
     */
    @RequestMapping("/companySettle_update/{companySettleId}")
    public String companySettleUpdate(@PathVariable Integer companySettleId, Model model) {
        CompanySettle companySettle = companySettleService.selectById(companySettleId);
        model.addAttribute("item",companySettle);
        LogObjectHolder.me().set(companySettle);
        return PREFIX + "companySettle_edit.html";
    }

    /**
     * 获取商户结算列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return companySettleService.selectList(null);
    }

    /**
     * 新增商户结算
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CompanySettle companySettle) {
        companySettleService.insert(companySettle);
        return SUCCESS_TIP;
    }

    /**
     * 删除商户结算
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer companySettleId) {
        companySettleService.deleteById(companySettleId);
        return SUCCESS_TIP;
    }

    /**
     * 修改商户结算
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CompanySettle companySettle) {
        companySettleService.updateById(companySettle);
        return SUCCESS_TIP;
    }

    /**
     * 商户结算详情
     */
    @RequestMapping(value = "/detail/{companySettleId}")
    @ResponseBody
    public Object detail(@PathVariable("companySettleId") Integer companySettleId) {
        return companySettleService.selectById(companySettleId);
    }
}
