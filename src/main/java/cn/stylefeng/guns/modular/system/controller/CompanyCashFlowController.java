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
import cn.stylefeng.guns.modular.system.model.CompanyCashFlow;
import cn.stylefeng.guns.modular.system.service.ICompanyCashFlowService;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-06-20 13:19:48
 */
@Controller
@RequestMapping("/companyCashFlow")
public class CompanyCashFlowController extends BaseController {

    private String PREFIX = "/system/companyCashFlow/";

    @Autowired
    private ICompanyCashFlowService companyCashFlowService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "companyCashFlow.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/companyCashFlow_add")
    public String companyCashFlowAdd() {
        return PREFIX + "companyCashFlow_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/companyCashFlow_update/{companyCashFlowId}")
    public String companyCashFlowUpdate(@PathVariable Integer companyCashFlowId, Model model) {
        CompanyCashFlow companyCashFlow = companyCashFlowService.selectById(companyCashFlowId);
        model.addAttribute("item",companyCashFlow);
        LogObjectHolder.me().set(companyCashFlow);
        return PREFIX + "companyCashFlow_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return companyCashFlowService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CompanyCashFlow companyCashFlow) {
        companyCashFlowService.insert(companyCashFlow);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer companyCashFlowId) {
        companyCashFlowService.deleteById(companyCashFlowId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CompanyCashFlow companyCashFlow) {
        companyCashFlowService.updateById(companyCashFlow);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{companyCashFlowId}")
    @ResponseBody
    public Object detail(@PathVariable("companyCashFlowId") Integer companyCashFlowId) {
        return companyCashFlowService.selectById(companyCashFlowId);
    }
}
