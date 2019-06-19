package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.model.Trade;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-13
 */
public interface ITradeService extends IService<Trade> {

    Trade selectTradeByOrderNo(String orderNo);

    void handleSucOrder(Integer flowNo);

    void createOrder(String orderNo);

    List<Trade> findTradeInfoWithInvalidOverTime(Integer num);

    void invalidOrder(String orderNo);

    List<Trade> findNeedAcountChangeOrders(Integer num);

    void handleAccountChange(Trade trade);
}
