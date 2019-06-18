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
 * @since 2019-06-18
 */
public class Scalper extends Model<Scalper> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 接受推送派单的唯一金主id
     */
    @TableField("scalper_id")
    private String scalperId;
    /**
     * 根代理id
     */
    @TableField("root_id")
    private String rootId;
    /**
     * 直接代理id
     */
    @TableField("upper_id")
    private String upperId;
    /**
     * 代理层级关系路径
     */
    @TableField("agent_path")
    private String agentPath;
    /**
     * 可用余额
     */
    @TableField("avaliable_balance")
    private BigDecimal avaliableBalance;
    /**
     * 冻结余额
     */
    @TableField("freeze_balance")
    private BigDecimal freezeBalance;
    /**
     * 总额
     */
    @TableField("total_balance")
    private BigDecimal totalBalance;
    /**
     * 当日累计收款
     */
    @TableField("collect_balance_sum_day")
    private BigDecimal collectBalanceSumDay;
    /**
     * 当日收款上限
     */
    @TableField("collect_balance_max_day")
    private BigDecimal collectBalanceMaxDay;
    /**
     * 最后一次派单时间
     */
    @TableField("last_assign_task_time")
    private Date lastAssignTaskTime;
    /**
     * 最后一次到账时间
     */
    @TableField("last_account_time")
    private Date lastAccountTime;
    /**
     * 最后一次充值时间
     */
    @TableField("last_charge_time")
    private Date lastChargeTime;
    /**
     * 最后一次信息流水推送时间
     */
    @TableField("last_flow_push_time")
    private Date lastFlowPushTime;
    /**
     * 金主支付宝开关
     */
    @TableField("alipay_switch")
    private Integer alipaySwitch;
    /**
     * 金主微信开关
     */
    @TableField("wx_switch")
    private Integer wxSwitch;
    /**
     * 金主银行卡开关
     */
    @TableField("bank_switch")
    private Integer bankSwitch;
    /**
     * 运营总控
     */
    @TableField("block_status")
    private Integer blockStatus;
    /**
     * 金主支付宝费率
     */
    @TableField("alipay_rate")
    private BigDecimal alipayRate;
    /**
     * 金主微信费率
     */
    @TableField("wx_rate")
    private BigDecimal wxRate;
    /**
     * 金主银行卡费率
     */
    @TableField("bank_rate")
    private BigDecimal bankRate;
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

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getUpperId() {
        return upperId;
    }

    public void setUpperId(String upperId) {
        this.upperId = upperId;
    }

    public String getAgentPath() {
        return agentPath;
    }

    public void setAgentPath(String agentPath) {
        this.agentPath = agentPath;
    }

    public BigDecimal getAvaliableBalance() {
        return avaliableBalance;
    }

    public void setAvaliableBalance(BigDecimal avaliableBalance) {
        this.avaliableBalance = avaliableBalance;
    }

    public BigDecimal getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(BigDecimal freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getCollectBalanceSumDay() {
        return collectBalanceSumDay;
    }

    public void setCollectBalanceSumDay(BigDecimal collectBalanceSumDay) {
        this.collectBalanceSumDay = collectBalanceSumDay;
    }

    public BigDecimal getCollectBalanceMaxDay() {
        return collectBalanceMaxDay;
    }

    public void setCollectBalanceMaxDay(BigDecimal collectBalanceMaxDay) {
        this.collectBalanceMaxDay = collectBalanceMaxDay;
    }

    public Date getLastAssignTaskTime() {
        return lastAssignTaskTime;
    }

    public void setLastAssignTaskTime(Date lastAssignTaskTime) {
        this.lastAssignTaskTime = lastAssignTaskTime;
    }

    public Date getLastAccountTime() {
        return lastAccountTime;
    }

    public void setLastAccountTime(Date lastAccountTime) {
        this.lastAccountTime = lastAccountTime;
    }

    public Date getLastChargeTime() {
        return lastChargeTime;
    }

    public void setLastChargeTime(Date lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
    }

    public Date getLastFlowPushTime() {
        return lastFlowPushTime;
    }

    public void setLastFlowPushTime(Date lastFlowPushTime) {
        this.lastFlowPushTime = lastFlowPushTime;
    }

    public Integer getAlipaySwitch() {
        return alipaySwitch;
    }

    public void setAlipaySwitch(Integer alipaySwitch) {
        this.alipaySwitch = alipaySwitch;
    }

    public Integer getWxSwitch() {
        return wxSwitch;
    }

    public void setWxSwitch(Integer wxSwitch) {
        this.wxSwitch = wxSwitch;
    }

    public Integer getBankSwitch() {
        return bankSwitch;
    }

    public void setBankSwitch(Integer bankSwitch) {
        this.bankSwitch = bankSwitch;
    }

    public Integer getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(Integer blockStatus) {
        this.blockStatus = blockStatus;
    }

    public BigDecimal getAlipayRate() {
        return alipayRate;
    }

    public void setAlipayRate(BigDecimal alipayRate) {
        this.alipayRate = alipayRate;
    }

    public BigDecimal getWxRate() {
        return wxRate;
    }

    public void setWxRate(BigDecimal wxRate) {
        this.wxRate = wxRate;
    }

    public BigDecimal getBankRate() {
        return bankRate;
    }

    public void setBankRate(BigDecimal bankRate) {
        this.bankRate = bankRate;
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
        return "Scalper{" +
        ", id=" + id +
        ", scalperId=" + scalperId +
        ", rootId=" + rootId +
        ", upperId=" + upperId +
        ", agentPath=" + agentPath +
        ", avaliableBalance=" + avaliableBalance +
        ", freezeBalance=" + freezeBalance +
        ", totalBalance=" + totalBalance +
        ", collectBalanceSumDay=" + collectBalanceSumDay +
        ", collectBalanceMaxDay=" + collectBalanceMaxDay +
        ", lastAssignTaskTime=" + lastAssignTaskTime +
        ", lastAccountTime=" + lastAccountTime +
        ", lastChargeTime=" + lastChargeTime +
        ", lastFlowPushTime=" + lastFlowPushTime +
        ", alipaySwitch=" + alipaySwitch +
        ", wxSwitch=" + wxSwitch +
        ", bankSwitch=" + bankSwitch +
        ", blockStatus=" + blockStatus +
        ", alipayRate=" + alipayRate +
        ", wxRate=" + wxRate +
        ", bankRate=" + bankRate +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        "}";
    }
}
