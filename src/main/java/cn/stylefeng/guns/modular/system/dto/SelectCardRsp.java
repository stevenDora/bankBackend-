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
public class SelectCardRsp {
    private BigDecimal amount;
    private String remark;
    private String cardNo;
    private String name;
    private String address;
    private String bankCode;
    private String bankName;
    private Long account_id;
}
