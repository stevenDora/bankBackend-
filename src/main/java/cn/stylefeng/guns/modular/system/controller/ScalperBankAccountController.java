package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.ScalperBankAccount;
import cn.stylefeng.guns.modular.system.service.IScalperBankAccountService;

import static cn.stylefeng.guns.core.shiro.ShiroKit.getUser;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-06-18 17:14:57
 */
@Controller
@RequestMapping("/scalperBankAccount")
public class ScalperBankAccountController extends BaseController {

    private String PREFIX = "/system/scalperBankAccount/";

    @Autowired
    private IScalperBankAccountService scalperBankAccountService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "scalperBankAccount.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/scalperBankAccount_add")
    public String scalperBankAccountAdd() {
        return PREFIX + "scalperBankAccount_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/scalperBankAccount_update/{scalperBankAccountId}")
    public String scalperBankAccountUpdate(@PathVariable Integer scalperBankAccountId, Model model) {
        ScalperBankAccount scalperBankAccount = scalperBankAccountService.selectById(scalperBankAccountId);
        model.addAttribute("item",scalperBankAccount);
        LogObjectHolder.me().set(scalperBankAccount);
        return PREFIX + "scalperBankAccount_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return scalperBankAccountService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ScalperBankAccount scalperBankAccount) {
        ShiroUser user = getUser();
        scalperBankAccount.setScalperId(user.getAccount());
        scalperBankAccountService.insert(scalperBankAccount);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer scalperBankAccountId) {
        scalperBankAccountService.deleteById(scalperBankAccountId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ScalperBankAccount scalperBankAccount) {
        scalperBankAccountService.updateById(scalperBankAccount);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{scalperBankAccountId}")
    @ResponseBody
    public Object detail(@PathVariable("scalperBankAccountId") Integer scalperBankAccountId) {
        return scalperBankAccountService.selectById(scalperBankAccountId);
    }
}
