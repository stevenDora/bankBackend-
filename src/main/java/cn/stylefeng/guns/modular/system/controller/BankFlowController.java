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
import cn.stylefeng.guns.modular.system.model.BankFlow;
import cn.stylefeng.guns.modular.system.service.IBankFlowService;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-05-30 16:32:52
 */
@Controller
@RequestMapping("/bankFlow")
public class BankFlowController extends BaseController {

    private String PREFIX = "/system/bankFlow/";

    @Autowired
    private IBankFlowService bankFlowService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bankFlow.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/bankFlow_add")
    public String bankFlowAdd() {
        return PREFIX + "bankFlow_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/bankFlow_update/{bankFlowId}")
    public String bankFlowUpdate(@PathVariable Integer bankFlowId, Model model) {
        BankFlow bankFlow = bankFlowService.selectById(bankFlowId);
        model.addAttribute("item",bankFlow);
        LogObjectHolder.me().set(bankFlow);
        return PREFIX + "bankFlow_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return bankFlowService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BankFlow bankFlow) {
        bankFlowService.insert(bankFlow);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer bankFlowId) {
        bankFlowService.deleteById(bankFlowId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BankFlow bankFlow) {
        bankFlowService.updateById(bankFlow);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{bankFlowId}")
    @ResponseBody
    public Object detail(@PathVariable("bankFlowId") Integer bankFlowId) {
        return bankFlowService.selectById(bankFlowId);
    }
}
