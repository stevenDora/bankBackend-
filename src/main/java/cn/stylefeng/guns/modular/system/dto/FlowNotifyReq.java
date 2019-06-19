package cn.stylefeng.guns.modular.system.dto;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlowNotifyReq {
    @NotNull(message = "scalper_id不能为空！")
    @JSONField(name = "scalper_id")
    private String scalper_id;

    @NotNull(message = "channel不能为空！")
    @JSONField(name = "channel")
    private Integer channel;

    @NotNull(message = "商户订单号不能为空！")
    @JSONField(name = "amount")
    private BigDecimal amount;

    @JSONField(name = "remark")
    private BigDecimal remark;

    @JSONField(name = "from_ip")
    private String from_ip;

    @JSONField(name = "receive_time")
    private Date receive_time;

    @JSONField(name = "data")
    private String data;

    private String sign;
    private String isSign;

}
