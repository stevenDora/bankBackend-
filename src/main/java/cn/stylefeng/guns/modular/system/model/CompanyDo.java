package cn.stylefeng.guns.modular.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDo implements Serializable {
    private Long id;
    private String address;
    private String charge;
    private String fixPhone;
    private String companyName;
    private String companyCode;
    private BigDecimal freezingAmount;
    private BigDecimal availableAmount;
    private BigDecimal totalAmount;
    private String email;
    private String website;
    private Byte status;
    private Timestamp crtTime;
    private Timestamp uptTime;
    private String note;
    private Byte ifRate;
    private String privateKey;
}
