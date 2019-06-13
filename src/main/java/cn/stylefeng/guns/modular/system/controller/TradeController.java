package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Trade;
import cn.stylefeng.guns.modular.system.service.ITradeService;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-06-13 15:40:08
 */
@Controller
@RequestMapping("/trade")
public class TradeController extends BaseController {

    private String PREFIX = "/system/trade/";

    @Autowired
    private ITradeService tradeService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "trade.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/trade_add")
    public String tradeAdd() {
        return PREFIX + "trade_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/trade_update/{tradeId}")
    public String tradeUpdate(@PathVariable Integer tradeId, Model model) {
        Trade trade = tradeService.selectById(tradeId);
        model.addAttribute("item",trade);
        LogObjectHolder.me().set(trade);
        return PREFIX + "trade_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return tradeService.selectList(null);
    }

    /**
     * 新增
     */
    @ApiOperation("新增订单")
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Trade trade) {
        tradeService.insert(trade);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tradeId) {
        tradeService.deleteById(tradeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Trade trade) {
        tradeService.updateById(trade);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{tradeId}")
    @ResponseBody
    public Object detail(@PathVariable("tradeId") Integer tradeId) {
        return tradeService.selectById(tradeId);
    }
}
