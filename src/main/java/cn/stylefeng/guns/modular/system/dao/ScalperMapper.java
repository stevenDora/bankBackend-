package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Scalper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
public interface ScalperMapper extends BaseMapper<Scalper> {

    String findBestScalper(Integer channel, BigDecimal amount);

    Integer updateScalperAccount(String scalper_id, BigDecimal amount);
}
