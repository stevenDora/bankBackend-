package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.dto.PayDepositReq;
import cn.stylefeng.guns.modular.system.model.Trade;

public interface PayApiService{
    Object deposit(PayDepositReq req);
    void createOrder(PayDepositReq req,String orderNo);

    Object getDepositDetail(String orderNo);
}
