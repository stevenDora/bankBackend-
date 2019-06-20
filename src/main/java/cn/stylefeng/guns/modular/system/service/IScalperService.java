package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.model.Scalper;
import cn.stylefeng.guns.modular.system.model.Trade;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
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

    public Scalper findScalperByScalperId(String scalper_id);

    void saveScalperCashFlow(Trade trade);

    String findBestScalper(Integer channel, BigDecimal amount);
}
