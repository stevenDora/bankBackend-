package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("scalper_cash_flow")
public class ScalperCashFlow extends Model<ScalperCashFlow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("scalper_id")
    private String scalperId;
    /**
     * 代收金额
     */
    @TableField("collection_amount")
    private BigDecimal collectionAmount;
    /**
     * 代付金额
     */
    @TableField("payment_amount")
    private BigDecimal paymentAmount;
    /**
     * 代收收益
     */
    @TableField("collection_earning")
    private BigDecimal collectionEarning;
    /**
     * 代付收益
     */
    @TableField("payment_earning")
    private BigDecimal paymentEarning;
    /**
     * 新可用余额
     */
    @TableField("updated_ab")
    private BigDecimal updatedAb;
    /**
     * 旧可用余额
     */
    @TableField("old_ab")
    private BigDecimal oldAb;
    /**
     * 新冻结金额
     */
    @TableField("updated_fa")
    private BigDecimal updatedFa;
    /**
     * 旧冻结金额
     */
    @TableField("old_fa")
    private BigDecimal oldFa;
    @TableField("transaction_time")
    private Date transactionTime;
    @TableField("order_no")
    private String orderNo;
    @TableField("order_status")
    private Integer orderStatus;
    @TableField("crt_time")
    private Date crtTime;
    @TableField("upt_time")
    private Date uptTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScalperId() {
        return scalperId;
    }

    public void setScalperId(String scalperId) {
        this.scalperId = scalperId;
    }

    public BigDecimal getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(BigDecimal collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getCollectionEarning() {
        return collectionEarning;
    }

    public void setCollectionEarning(BigDecimal collectionEarning) {
        this.collectionEarning = collectionEarning;
    }

    public BigDecimal getPaymentEarning() {
        return paymentEarning;
    }

    public void setPaymentEarning(BigDecimal paymentEarning) {
        this.paymentEarning = paymentEarning;
    }

    public BigDecimal getUpdatedAb() {
        return updatedAb;
    }

    public void setUpdatedAb(BigDecimal updatedAb) {
        this.updatedAb = updatedAb;
    }

    public BigDecimal getOldAb() {
        return oldAb;
    }

    public void setOldAb(BigDecimal oldAb) {
        this.oldAb = oldAb;
    }

    public BigDecimal getUpdatedFa() {
        return updatedFa;
    }

    public void setUpdatedFa(BigDecimal updatedFa) {
        this.updatedFa = updatedFa;
    }

    public BigDecimal getOldFa() {
        return oldFa;
    }

    public void setOldFa(BigDecimal oldFa) {
        this.oldFa = oldFa;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ScalperCashFlow{" +
        ", id=" + id +
        ", scalperId=" + scalperId +
        ", collectionAmount=" + collectionAmount +
        ", paymentAmount=" + paymentAmount +
        ", collectionEarning=" + collectionEarning +
        ", paymentEarning=" + paymentEarning +
        ", updatedAb=" + updatedAb +
        ", oldAb=" + oldAb +
        ", updatedFa=" + updatedFa +
        ", oldFa=" + oldFa +
        ", transactionTime=" + transactionTime +
        ", orderNo=" + orderNo +
        ", orderStatus=" + orderStatus +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        "}";
    }
}
