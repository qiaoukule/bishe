package cc.mrbird.febs.cos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 车位管理
 *
 * @author QQ
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParkInfo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 停车位编号
     */
    private String parkNum;

    /**
     * 使用状态
     */
    private Integer status;

    /**
     * 车牌
     */
    private String carNum;

    /**
     * 车位主
     */
    private String owner;

    /**
     * 主的电话
     */
    private String phone;

    /**
     * 所属苑区
     */
    private String community;

    /**
     * 租出时间/售出时间
     */
    private String ownDate;

    /**
     * 车位面积
     */
    private BigDecimal area;


}
