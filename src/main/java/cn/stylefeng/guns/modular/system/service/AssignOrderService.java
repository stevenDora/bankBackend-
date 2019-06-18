package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.dto.AccountSelectRsp;

import java.math.BigDecimal;

public interface AssignOrderService {
    public AccountSelectRsp accountSelect(String orderNo,Integer channel, BigDecimal amount);
}
