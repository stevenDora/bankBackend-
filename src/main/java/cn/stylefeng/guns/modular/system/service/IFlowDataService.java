package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.dto.FlowNotifyReq;
import cn.stylefeng.guns.modular.system.model.FlowData;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
public interface IFlowDataService extends IService<FlowData> {

    Integer save(FlowNotifyReq req);

    FlowData getFlowDataById(Integer flowNo);

    Integer updateFlowData(Integer flowNo,String matchedOrderNo);
}
