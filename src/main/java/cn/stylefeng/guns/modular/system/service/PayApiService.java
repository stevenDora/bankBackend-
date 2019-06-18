package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.dto.FlowNotifyReq;
import cn.stylefeng.guns.modular.system.dto.PayDepositReq;
import cn.stylefeng.guns.modular.system.model.Trade;
import org.springframework.ui.Model;


public interface PayApiService{
    Object deposit(PayDepositReq req);

    Object getDepositDetail(String orderNo);

    Object getDepositResult(String orderNo);

    Object notify(FlowNotifyReq flowNotifyReq);
}
