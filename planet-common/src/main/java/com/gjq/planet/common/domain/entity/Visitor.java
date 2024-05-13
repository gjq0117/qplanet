package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 网站访问信息
 * </p>
 *
 * @author gjq
 * @since 2024-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("visitor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Long uid;

    /**
     * IP
     */
    @TableField("ip")
    private String ip;

    /**
     * 国家
     */
    @TableField("nation")
    private String nation;

    /**
     * 省份
     */
    @TableField("province")
    private String province;

    /**
     * 城市
     */
    @TableField("city")
    private String city;

    /**
     * 访问的资源ID
     */
    @TableField("resource_id")
    private Long resourceId;

    /**
     * 访问的资源类型【0：文章；1：专栏】
     */
    @TableField("resource_type")
    private Integer resourceType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除【0：未删除；1：删除】
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
