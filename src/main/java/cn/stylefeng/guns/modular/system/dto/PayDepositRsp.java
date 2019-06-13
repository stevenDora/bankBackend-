package cn.stylefeng.guns.modular.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayDepositRsp {
    private Long merchant_id;
    private String merchant_order_no;
    private String platform_order_no;
    private BigDecimal amount;
    private Byte channel;
    private Date invalidDate;
    private String payUrl;
}
