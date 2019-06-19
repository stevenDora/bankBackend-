package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.model.Scalper;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
public interface IScalperService extends IService<Scalper> {

    public Map<String,Object> findScalperByScalperId(String scalper_id);
}
