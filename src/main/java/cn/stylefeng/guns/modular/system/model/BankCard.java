package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author steven123
 * @since 2019-05-27
 */
@TableName("bank_card")
public class BankCard extends Model<BankCard> {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableField("card_no")
    private String cardNo;

    private String name;

    private String address;

    @TableField("bank_code")
    private String bankCode;

    private Byte status;

    @TableField("online_status")
    private Byte onlineStatus;

    @TableField("avaliable_amount")
    private BigDecimal avaliableAmount;

    @TableField("recv_amount")
    private BigDecimal recvAmount;

    @TableField("pending_amount")
    private BigDecimal pendingAmount;

    @TableField("last_transaction_time")
    private Date lastTransactionTime;

    @TableField("limit_amount")
    private BigDecimal limitAmount;

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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Byte onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public BigDecimal getAvaliableAmount() {
        return avaliableAmount;
    }

    public void setAvaliableAmount(BigDecimal avaliableAmount) {
        this.avaliableAmount = avaliableAmount;
    }

    public BigDecimal getRecvAmount() {
        return recvAmount;
    }

    public void setRecvAmount(BigDecimal recvAmount) {
        this.recvAmount = recvAmount;
    }

    public BigDecimal getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(BigDecimal pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public Date getLastTransactionTime() {
        return lastTransactionTime;
    }

    public void setLastTransactionTime(Date lastTransactionTime) {
        this.lastTransactionTime = lastTransactionTime;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", cardNo='" + cardNo + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", status=" + status +
                ", onlineStatus=" + onlineStatus +
                ", avaliableAmount=" + avaliableAmount +
                ", recvAmount=" + recvAmount +
                ", pendingAmount=" + pendingAmount +
                ", lastTransactionTime=" + lastTransactionTime +
                ", limitAmount=" + limitAmount +
                ", crtTime=" + crtTime +
                ", uptTime=" + uptTime +
                '}';
    }
}
