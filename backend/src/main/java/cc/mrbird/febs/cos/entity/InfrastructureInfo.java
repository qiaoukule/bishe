package cc.mrbird.febs.cos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 增添基础设施
 *
 * @author QQ
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InfrastructureInfo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     *设施名称
     */
    private String infrastructureName;


    /**
     * 投入使用时间
     */
    private Date useDate;


    /**
     * 苑区
     */
    private String community;

    /**
     * 牌子
     */
    private String brand;

    private String createDate;
}