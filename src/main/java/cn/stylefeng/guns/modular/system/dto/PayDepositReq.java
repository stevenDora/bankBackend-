package cn.stylefeng.guns.modular.system.dto;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayDepositReq {
    @NotNull(message = "商户ID不能为空！")
    @JSONField(name = "merchant_id")
    private Long merchantId;

    @NotNull(message = "商户订单号不能为空！")
    @JSONField(name = "merchant_order_no")
    private String merchantOrderNo;

    @NotNull(message = "充值金额不能为空！")
    @JSONField(name = "amount")
    private BigDecimal amount;

    @NotNull(message = "渠道编码不能为空！")
    @JSONField(name = "channel")
    private Byte channel;

    @NotNull(message = "api版本号不能为空！")
    @JSONField(name = "api_version")
    private String apiVersion;

    @JSONField(name = "notify_url")
    private String notifyUrl;

    @NotNull(message = "时间戳不能为空！")
    private String timestamp;

    @NotNull(message = "签名不能为空！")
    private String sign;

    private String isSign;
}
