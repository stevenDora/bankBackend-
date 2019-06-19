package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.model.Scalper;
import cn.stylefeng.guns.modular.system.dao.ScalperMapper;
import cn.stylefeng.guns.modular.system.service.IScalperService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
@Service
public class ScalperServiceImpl extends ServiceImpl<ScalperMapper, Scalper> implements IScalperService {

    @Autowired
    private ScalperMapper scalperMapper;

    @Override
    public Map<String,Object> findScalperByScalperId(String scalper_id) {
        return scalperMapper.findScalperByScalperId(scalper_id);
    }
}
