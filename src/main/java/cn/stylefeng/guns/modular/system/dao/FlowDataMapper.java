package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.dto.FlowNotifyReq;
import cn.stylefeng.guns.modular.system.model.FlowData;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
public interface FlowDataMapper extends BaseMapper<FlowData> {

    Integer save(FlowNotifyReq req);

    @Select("select id,scalper_id,channel,amount,remark,from_ip,receive_time," +
            "is_matched from flow_data where id = #{flowNo} and is_matched = 0")
    FlowData getFlowDataById(Integer flowNo);

    @Update("update flow_data set is_matched = 1," +
            "order_no = #{matched_order_no} where id = #{flowNo} and is_matched = 0")
    Integer updateFlowData(Integer flowNo,String matched_order_no);
}
