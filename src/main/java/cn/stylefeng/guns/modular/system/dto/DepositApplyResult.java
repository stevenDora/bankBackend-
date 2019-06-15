package cn.stylefeng.guns.modular.system.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositApplyResult {
    private String orderNo;
    private Integer orderStatus;
    private Integer channel;
}
