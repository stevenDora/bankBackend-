package cn.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import cn.stylefeng.guns.modular.system.model.BankRemark;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-29
 */
public interface IBankRemarkService extends IService<BankRemark> {

    String genRemark();
}
