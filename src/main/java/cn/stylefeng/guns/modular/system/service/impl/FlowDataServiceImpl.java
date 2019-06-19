package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dto.FlowNotifyReq;
import cn.stylefeng.guns.modular.system.model.FlowData;
import cn.stylefeng.guns.modular.system.dao.FlowDataMapper;
import cn.stylefeng.guns.modular.system.service.IFlowDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
@Service
public class FlowDataServiceImpl extends ServiceImpl<FlowDataMapper, FlowData> implements IFlowDataService {

    @Autowired
    private FlowDataMapper flowDataMapper;

    @Override
    public Integer save(FlowNotifyReq req) {
        return flowDataMapper.save(req);
    }

    @Override
    public FlowData getFlowDataById(Integer flowNo) {
        return flowDataMapper.getFlowDataById(flowNo);
    }

    @Override
    public Integer updateFlowData(Integer flowNo,String matchedOrderNo) {
        return flowDataMapper.updateFlowData(flowNo,matchedOrderNo);
    }
}
