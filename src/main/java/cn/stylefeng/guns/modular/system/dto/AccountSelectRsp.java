package cn.stylefeng.guns.modular.system.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSelectRsp {
    private BigDecimal amount;
    private Integer channel;
    private String account_info;
    private Long account_id;
    private String remark;
}
