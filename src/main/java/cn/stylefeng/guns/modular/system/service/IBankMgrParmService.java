package cn.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import cn.stylefeng.guns.modular.system.model.BankMgrParm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-30
 */
public interface IBankMgrParmService extends IService<BankMgrParm> {

    Integer getLastCursor();

    Integer getBatchNum();

    void updateLastCursor(Integer value);
}
