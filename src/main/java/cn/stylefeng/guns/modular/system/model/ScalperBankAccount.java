package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-06-18
 */
@TableName("scalper_bank_account")
public class ScalperBankAccount extends Model<ScalperBankAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 金主id
     */
    @TableField("scalper_id")
    private String scalperId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 卡号
     */
    @TableField("card_no")
    private String cardNo;
    /**
     * 银行名称
     */
    private String bank;
    /**
     * 银行编号
     */
    @TableField("bank_code")
    private String bankCode;
    /**
     * 发卡行地址
     */
    @TableField("card_addr")
    private String cardAddr;
    /**
     * 激活状态 0:未激活 1:激活
     */
    private Integer status;
    @TableField("crt_time")
    private Date crtTime;
    @TableField("upt_time")
    private Date uptTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScalperId() {
        return scalperId;
    }

    public void setScalperId(String scalperId) {
        this.scalperId = scalperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardAddr() {
        return cardAddr;
    }

    public void setCardAddr(String cardAddr) {
        this.cardAddr = cardAddr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "ScalperBankAccount{" +
        ", id=" + id +
        ", scalperId=" + scalperId +
        ", name=" + name +
        ", cardNo=" + cardNo +
        ", bank=" + bank +
        ", bankCode=" + bankCode +
        ", cardAddr=" + cardAddr +
        ", status=" + status +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        "}";
    }
}
