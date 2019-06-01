package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("bank_flow")
public class BankFlow extends Model<BankFlow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 卡号
     */
    @TableField("card_no")
    private String cardNo;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 可用余额
     */
    @TableField("avaliable_balance")
    private BigDecimal avaliableBalance;
    /**
     * 对方卡号
     */
    @TableField("peer_card_no")
    private String peerCardNo;
    @TableField("transaction_time")
    private Date transactionTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 處理狀態
     */
    private Byte status;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAvaliableBalance() {
        return avaliableBalance;
    }

    public void setAvaliableBalance(BigDecimal avaliableBalance) {
        this.avaliableBalance = avaliableBalance;
    }

    public String getPeerCardNo() {
        return peerCardNo;
    }

    public void setPeerCardNo(String peerCardNo) {
        this.peerCardNo = peerCardNo;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
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


    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BankFlow{" +
                "cardNo='" + cardNo + '\'' +
                ", amount=" + amount +
                ", avaliableBalance=" + avaliableBalance +
                ", peerCardNo='" + peerCardNo + '\'' +
                ", transactionTime=" + transactionTime +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", crtTime=" + crtTime +
                ", uptTime=" + uptTime +
                '}';
    }
}
