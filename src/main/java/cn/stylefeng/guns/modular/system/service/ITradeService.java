package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.model.Trade;
import com.baomidou.mybatisplus.service.IService;

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

    void createOrder(String orderNo);
}
