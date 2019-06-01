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
public class BankCashflowVo {
    private String cardNo;
    private BigDecimal amount;
    private BigDecimal avaliable_balance;
    private String peer_card_no;
    private Date transaction_time;
    private String remark;
}
