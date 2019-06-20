package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.dto.AccountSelectRsp;
import cn.stylefeng.guns.modular.system.model.Trade;

import java.math.BigDecimal;

public interface AssignOrderService {
    public AccountSelectRsp accountSelect(Trade trade);
}
