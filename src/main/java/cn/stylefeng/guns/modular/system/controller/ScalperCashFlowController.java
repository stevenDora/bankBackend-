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
import cn.stylefeng.guns.modular.system.model.ScalperCashFlow;
import cn.stylefeng.guns.modular.system.service.IScalperCashFlowService;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-06-20 14:57:34
 */
@Controller
@RequestMapping("/scalperCashFlow")
public class ScalperCashFlowController extends BaseController {

    private String PREFIX = "/system/scalperCashFlow/";

    @Autowired
    private IScalperCashFlowService scalperCashFlowService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "scalperCashFlow.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/scalperCashFlow_add")
    public String scalperCashFlowAdd() {
        return PREFIX + "scalperCashFlow_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/scalperCashFlow_update/{scalperCashFlowId}")
    public String scalperCashFlowUpdate(@PathVariable Integer scalperCashFlowId, Model model) {
        ScalperCashFlow scalperCashFlow = scalperCashFlowService.selectById(scalperCashFlowId);
        model.addAttribute("item",scalperCashFlow);
        LogObjectHolder.me().set(scalperCashFlow);
        return PREFIX + "scalperCashFlow_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return scalperCashFlowService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ScalperCashFlow scalperCashFlow) {
        scalperCashFlowService.insert(scalperCashFlow);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer scalperCashFlowId) {
        scalperCashFlowService.deleteById(scalperCashFlowId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ScalperCashFlow scalperCashFlow) {
        scalperCashFlowService.updateById(scalperCashFlow);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{scalperCashFlowId}")
    @ResponseBody
    public Object detail(@PathVariable("scalperCashFlowId") Integer scalperCashFlowId) {
        return scalperCashFlowService.selectById(scalperCashFlowId);
    }
}
