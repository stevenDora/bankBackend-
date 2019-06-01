package cn.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import cn.stylefeng.guns.modular.system.model.BankFlow;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-29
 */
public interface IBankFlowService extends IService<BankFlow> {

    void processCashflows();
}
