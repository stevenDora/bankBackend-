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
import cn.stylefeng.guns.modular.system.model.Scalper;
import cn.stylefeng.guns.modular.system.service.IScalperService;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-06-18 15:00:10
 */
@Controller
@RequestMapping("/scalper")
public class ScalperController extends BaseController {

    private String PREFIX = "/system/scalper/";

    @Autowired
    private IScalperService scalperService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "scalper.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/scalper_add")
    public String scalperAdd() {
        return PREFIX + "scalper_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/scalper_update/{scalperId}")
    public String scalperUpdate(@PathVariable Integer scalperId, Model model) {
        Scalper scalper = scalperService.selectById(scalperId);
        model.addAttribute("item",scalper);
        LogObjectHolder.me().set(scalper);
        return PREFIX + "scalper_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return scalperService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Scalper scalper) {
        scalperService.insert(scalper);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer scalperId) {
        scalperService.deleteById(scalperId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Scalper scalper) {
        scalperService.updateById(scalper);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{scalperId}")
    @ResponseBody
    public Object detail(@PathVariable("scalperId") Integer scalperId) {
        return scalperService.selectById(scalperId);
    }
}
