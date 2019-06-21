package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-21
 */
@TableName("company_settle")
public class CompanySettle extends Model<CompanySettle> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("order_no")
    private String orderNo;
    @TableField("mow_order_no")
    private String mowOrderNo;
    @TableField("apply_amount")
    private BigDecimal applyAmount;
    @TableField("true_amount")
    private BigDecimal trueAmount;
    @TableField("service_fee")
    private BigDecimal serviceFee;
    @TableField("bank_code")
    private String bankCode;
    @TableField("bank_name")
    private String bankName;
    @TableField("sub_bank")
    private String subBank;
    @TableField("bank_card_no")
    private String bankCardNo;
    @TableField("account_name")
    private String accountName;
    /**
     * 1 processing 2 success 3 failed 4 verifying
     */
    private String status;
    /**
     * 0 自行 1 三方
     */
    @TableField("process_way")
    private String processWay;
    @TableField("company_id")
    private Long companyId;
    private String remark;
    @TableField("crt_time")
    private Date crtTime;
    @TableField("upt_time")
    private Date uptTime;
    @TableField("trans_time")
    private Date transTime;
    /**
     * MA编号
     */
    @TableField("ma_no")
    private String maNo;
    /**
     * 商户结算附言
     */
    private String note;
    /**
     * 商户结算运营操作员
     */
    private String operator;
    /**
     * 商户结算订单超时时间
     */
    @TableField("over_time")
    private Date overTime;
    /**
     * 抢单金主ID
     */
    @TableField("scalper_id")
    private Long scalperId;
    /**
     * 对充金主交易流水单截图
     */
    @TableField("scalper_image_url")
    private String scalperImageUrl;
    /**
     * 订单金主手机号
     */
    private String phone;
    /**
     * 订单结算币种
     */
    private String currency;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMowOrderNo() {
        return mowOrderNo;
    }

    public void setMowOrderNo(String mowOrderNo) {
        this.mowOrderNo = mowOrderNo;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public BigDecimal getTrueAmount() {
        return trueAmount;
    }

    public void setTrueAmount(BigDecimal trueAmount) {
        this.trueAmount = trueAmount;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSubBank() {
        return subBank;
    }

    public void setSubBank(String subBank) {
        this.subBank = subBank;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessWay() {
        return processWay;
    }

    public void setProcessWay(String processWay) {
        this.processWay = processWay;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getMaNo() {
        return maNo;
    }

    public void setMaNo(String maNo) {
        this.maNo = maNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public Long getScalperId() {
        return scalperId;
    }

    public void setScalperId(Long scalperId) {
        this.scalperId = scalperId;
    }

    public String getScalperImageUrl() {
        return scalperImageUrl;
    }

    public void setScalperImageUrl(String scalperImageUrl) {
        this.scalperImageUrl = scalperImageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CompanySettle{" +
        ", id=" + id +
        ", orderNo=" + orderNo +
        ", mowOrderNo=" + mowOrderNo +
        ", applyAmount=" + applyAmount +
        ", trueAmount=" + trueAmount +
        ", serviceFee=" + serviceFee +
        ", bankCode=" + bankCode +
        ", bankName=" + bankName +
        ", subBank=" + subBank +
        ", bankCardNo=" + bankCardNo +
        ", accountName=" + accountName +
        ", status=" + status +
        ", processWay=" + processWay +
        ", companyId=" + companyId +
        ", remark=" + remark +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        ", transTime=" + transTime +
        ", maNo=" + maNo +
        ", note=" + note +
        ", operator=" + operator +
        ", overTime=" + overTime +
        ", scalperId=" + scalperId +
        ", scalperImageUrl=" + scalperImageUrl +
        ", phone=" + phone +
        ", currency=" + currency +
        "}";
    }
}
