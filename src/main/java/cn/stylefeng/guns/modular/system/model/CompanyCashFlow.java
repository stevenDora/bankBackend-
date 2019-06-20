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
@TableName("company_cash_flow")
public class CompanyCashFlow extends Model<CompanyCashFlow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("company_id")
    private Integer companyId;
    /**
     * 玩家充值金额
     */
    @TableField("collection_amount")
    private BigDecimal collectionAmount;
    /**
     * 玩家提现金额
     */
    @TableField("payment_amount")
    private BigDecimal paymentAmount;
    /**
     * 玩家充值商户手续费
     */
    @TableField("collection_bfs")
    private BigDecimal collectionBfs;
    /**
     * 玩家提现商户手续费
     */
    @TableField("payment_bfs")
    private BigDecimal paymentBfs;
    /**
     * 新可以用余额
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
    @TableField("transaction_date")
    private Date transactionDate;
    @TableField("order_no")
    private String orderNo;
    @TableField("crt_time")
    private Date crtTime;
    @TableField("upt_time")
    private Date uptTime;
    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer orderStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public BigDecimal getCollectionBfs() {
        return collectionBfs;
    }

    public void setCollectionBfs(BigDecimal collectionBfs) {
        this.collectionBfs = collectionBfs;
    }

    public BigDecimal getPaymentBfs() {
        return paymentBfs;
    }

    public void setPaymentBfs(BigDecimal paymentBfs) {
        this.paymentBfs = paymentBfs;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CompanyCashFlow{" +
        ", id=" + id +
        ", companyId=" + companyId +
        ", collectionAmount=" + collectionAmount +
        ", paymentAmount=" + paymentAmount +
        ", collectionBfs=" + collectionBfs +
        ", paymentBfs=" + paymentBfs +
        ", updatedAb=" + updatedAb +
        ", oldAb=" + oldAb +
        ", updatedFa=" + updatedFa +
        ", oldFa=" + oldFa +
        ", transactionDate=" + transactionDate +
        ", orderNo=" + orderNo +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        ", orderStatus=" + orderStatus +
        "}";
    }
}
