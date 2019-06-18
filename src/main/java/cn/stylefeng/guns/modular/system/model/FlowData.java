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
 * @since 2019-06-18
 */
@TableName("flow_data")
public class FlowData extends Model<FlowData> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 金主ID
     */
    @TableField("scalper_id")
    private String scalperId;
    /**
     * 渠道
     */
    private Integer channel;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 附言
     */
    private String remark;
    /**
     * 源ip
     */
    @TableField("from_ip")
    private String fromIp;
    /**
     * 到账时间
     */
    @TableField("receive_time")
    private Date receiveTime;
    /**
     * 详细信息
     */
    private String data;
    /**
     * 匹配上的订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 是否匹配
     */
    @TableField("is_matched")
    private Integer isMatched;
    /**
     * 接收时间
     */
    @TableField("crt_time")
    private Date crtTime;
    /**
     * 更新时间
     */
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

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getIsMatched() {
        return isMatched;
    }

    public void setIsMatched(Integer isMatched) {
        this.isMatched = isMatched;
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
        return "FlowData{" +
        ", id=" + id +
        ", scalperId=" + scalperId +
        ", channel=" + channel +
        ", amount=" + amount +
        ", remark=" + remark +
        ", fromIp=" + fromIp +
        ", receiveTime=" + receiveTime +
        ", data=" + data +
        ", orderNo=" + orderNo +
        ", isMatched=" + isMatched +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        "}";
    }
}
