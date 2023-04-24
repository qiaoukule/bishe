package cc.mrbird.febs.cos.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 基础设施管理
 *
 * @author QQ
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InfrastructureManage {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 和上面的id是一个id 嘤
     */
    @TableField(exist = false)
    private Integer recordId;

    /**
     *设施id
     */
    private Integer infrastructure_id;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    private String startDate;

    /**
     * 结束时间
     */
    @TableField(exist = false)
    private String endDate;

    /**
     * 备注
     */
    private String message;

    /**
     * 状态 1.检查合格 2.瓦特了 3.设备过期了
     */
    private Integer status;

    /**
     * 工人
     */
    private Integer worker;

    /**
     * 苑区
     */
    private String community;

    /**
     * 修改时需要用到的date
     */
    private String date;

}
