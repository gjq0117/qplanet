package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 标签信息
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("label")
public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类ID
     */
    @TableField("sort_id")
    private Long sortId;

    /**
     * 标签名
     */
    @TableField("label_name")
    private String labelName;

    /**
     * 标签描述
     */
    @TableField("label_description")
    private String labelDescription;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除【0：未删除；1：删除】
     */
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;


}
