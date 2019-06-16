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
public class DepositAccountDetailRsp {
    private String orderNo;
    private BigDecimal amount;
    private String remark;
    private Integer channel;
    private String account;
    private String name;
/*    private String qrCode;*/
    private Date createDate;
    private Date overDueDate;
}
