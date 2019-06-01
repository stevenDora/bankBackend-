package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
@TableName("bank_remark")
public class BankRemark extends Model<BankRemark> {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String remark;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BankRemark{" +
        "id=" + id +
        ", remark=" + remark +
        ", crtTime=" + crtTime +
        ", uptTime=" + uptTime +
        "}";
    }
}
