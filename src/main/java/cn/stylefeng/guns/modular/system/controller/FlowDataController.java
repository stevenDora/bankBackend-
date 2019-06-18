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
import cn.stylefeng.guns.modular.system.model.FlowData;
import cn.stylefeng.guns.modular.system.service.IFlowDataService;

/**
 * 新流水显示控制器
 *
 * @author fengshuonan
 * @Date 2019-06-18 19:03:13
 */
@Controller
@RequestMapping("/flowData")
public class FlowDataController extends BaseController {

    private String PREFIX = "/system/flowData/";

    @Autowired
    private IFlowDataService flowDataService;

    /**
     * 跳转到新流水显示首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "flowData.html";
    }

    /**
     * 跳转到添加新流水显示
     */
    @RequestMapping("/flowData_add")
    public String flowDataAdd() {
        return PREFIX + "flowData_add.html";
    }

    /**
     * 跳转到修改新流水显示
     */
    @RequestMapping("/flowData_update/{flowDataId}")
    public String flowDataUpdate(@PathVariable Integer flowDataId, Model model) {
        FlowData flowData = flowDataService.selectById(flowDataId);
        model.addAttribute("item",flowData);
        LogObjectHolder.me().set(flowData);
        return PREFIX + "flowData_edit.html";
    }

    /**
     * 获取新流水显示列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return flowDataService.selectList(null);
    }

    /**
     * 新增新流水显示
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(FlowData flowData) {
        flowDataService.insert(flowData);
        return SUCCESS_TIP;
    }

    /**
     * 删除新流水显示
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer flowDataId) {
        flowDataService.deleteById(flowDataId);
        return SUCCESS_TIP;
    }

    /**
     * 修改新流水显示
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(FlowData flowData) {
        flowDataService.updateById(flowData);
        return SUCCESS_TIP;
    }

    /**
     * 新流水显示详情
     */
    @RequestMapping(value = "/detail/{flowDataId}")
    @ResponseBody
    public Object detail(@PathVariable("flowDataId") Integer flowDataId) {
        return flowDataService.selectById(flowDataId);
    }
}
