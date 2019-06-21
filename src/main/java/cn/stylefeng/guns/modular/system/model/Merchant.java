package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-21
 */
public class Merchant extends Model<Merchant> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 地址
     */
    private String address;
    /**
     * 负责人
     */
    private String charge;
    /**
     * 固定电话
     */
    @TableField("fix_phone")
    private String fixPhone;
    /**
     * 商户名称
     */
    @TableField("company_name")
    private String companyName;
    /**
     * 商户编码
     */
    @TableField("company_code")
    private String companyCode;
    /**
     * 冻结金额
     */
    @TableField("freezing_amount")
    private BigDecimal freezingAmount;
    /**
     * 可用金额
     */
    @TableField("available_amount")
    private BigDecimal availableAmount;
    /**
     * 总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 网站
     */
    private String website;
    /**
     * 1
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("crt_time")
    private Date crtTime;
    /**
     * 修改时间
     */
    @TableField("upt_time")
    private Date uptTime;
    /**
     * 备注
     */
    private String note;
    /**
     * 0代表未配置过费率
1代表已配置过费率（运营后台无法配置商户费率）
     */
    @TableField("if_rate")
    private Integer ifRate;
    /**
     * 私钥
     */
    @TableField("private_key")
    private String privateKey;
    /**
     * 商户bsp密码
     */
    private String password;
    /**
     * 结算密码
     */
    @TableField("settle_pwd")
    private String settlePwd;
    /**
     * 商户结算是否启用手机验证
     */
    @TableField("is_verify_phone")
    private String isVerifyPhone;
    /**
     * 接收验证码的电话码
     */
    @TableField("verify_phone")
    private String verifyPhone;
    /**
     * 是否开启对充开关，0否，1是
     */
    @TableField("is_open_dc")
    private String isOpenDc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getFixPhone() {
        return fixPhone;
    }

    public void setFixPhone(String fixPhone) {
        this.fixPhone = fixPhone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public BigDecimal getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(BigDecimal freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getIfRate() {
        return ifRate;
    }

    public void setIfRate(Integer ifRate) {
        this.ifRate = ifRate;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSettlePwd() {
        return settlePwd;
    }

    public void setSettlePwd(String settlePwd) {
        this.settlePwd = settlePwd;
    }

    public String getIsVerifyPhone() {
        return isVerifyPhone;
    }

    public void setIsVerifyPhone(String isVerifyPhone) {
        this.isVerifyPhone = isVerifyPhone;
    }

    public String getVerifyPhone() {
        return verifyPhone;
    }

    public void setVerifyPhone(String verifyPhone) {
        this.verifyPhone = verifyPhone;
    }

    public String getIsOpenDc() {
        return isOpenDc;
    }

    public void setIsOpenDc(String isOpenDc) {
        this.isOpenDc = isOpenDc;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Merchant{" +
        ", id=" + id +
        ", address=" + address +
        ", charge=" + charge +
        ", fixPhone=" + fixPhone +
        ", companyName=" + companyName +
        ", companyCode=" + companyCode +
        ", freezingAmount=" + freezingAmount +
        ", availableAmount=" + availableAmount +
        ", totalAmount=" + totalAmount +
        ", email=" + email +
        ", website=" + website +
        ", status=" + status +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        ", note=" + note +
        ", ifRate=" + ifRate +
        ", privateKey=" + privateKey +
        ", password=" + password +
        ", settlePwd=" + settlePwd +
        ", isVerifyPhone=" + isVerifyPhone +
        ", verifyPhone=" + verifyPhone +
        ", isOpenDc=" + isOpenDc +
        "}";
    }
}
