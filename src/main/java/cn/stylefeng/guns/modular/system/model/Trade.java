package cn.stylefeng.guns.modular.system.model;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.models.auth.In;
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
 * @since 2019-06-13
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trade extends Model<Trade> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    *  账号Id
    */
    @TableField(value = "account_id")
    private Long accountId;

    /**
     *  账号详情 冗余字段
     */
    @TableField(value = "account_info")
    private String accountInfo;

    /**
     * 平台订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 商户编号
     */
    @TableField("company_no")
    private Integer companyNo;
    /**
     * 商户订单号
     */
    @TableField("company_order_no")
    private String companyOrderNo;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    /**
     * 充值渠道类型:1:zfb 2:wx 3:yhk
     */
    @TableField("channel")
    private Integer channel;

    /**
     * 申请金额
     */
    @TableField("apply_amount")
    private BigDecimal applyAmount;
    /**
     * 到账金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;
    /**
     * 商户手续费
     */
    @TableField("service_fee")
    private BigDecimal serviceFee;
    /**
     * 订单状态:1:processing 2:success 3:overtime
     */
    @TableField("order_status")
    private Integer orderStatus;
    /**
     * 到账时间
     */
    @TableField("arrive_time")
    private Date arriveTime;
    /**
     * 回调时间
     */
    @TableField("push_time")
    private Date pushTime;
    /**
     * 回调状态
     */
    @TableField("push_status")
    private Integer pushStatus;
    /**
     * 附言
     */
    @TableField("remark")
    private String remark;
    /**
     * 创建时间
     */
    @TableField("crt_time")
    private Date crtTime;
    /**
     * 最后一次更新时间
     */
    @TableField("upt_time")
    private Date uptTime;


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

    public String getCompanyOrderNo() {
        return companyOrderNo;
    }

    public void setCompanyOrderNo(String companyOrderNo) {
        this.companyOrderNo = companyOrderNo;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
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
        return "Trade{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", companyNo='" + companyNo + '\'' +
                ", companyOrderNo='" + companyOrderNo + '\'' +
                ", channel=" + channel +
                ", applyAmount=" + applyAmount +
                ", actualAmount=" + actualAmount +
                ", serviceFee=" + serviceFee +
                ", orderStatus=" + orderStatus +
                ", arriveTime=" + arriveTime +
                ", pushTime=" + pushTime +
                ", pushStatus=" + pushStatus +
                ", crtTime=" + crtTime +
                ", uptTime=" + uptTime +
                '}';
    }
}
