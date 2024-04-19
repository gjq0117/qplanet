package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 网站信息
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_info")
public class WebInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网站名
     */
    @TableField("web_name")
    private String webName;

    /**
     * 作者名
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 格言/座右铭/箴言（可配置多条，用封号间隔）
     */
    @TableField("mottos")
    private String mottos;

    /**
     * 公告（可配置多条，用封号间隔）
     */
    @TableField("notices")
    private String notices;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 背景
     */
    @TableField("background_image")
    private String backgroundImage;

    /**
     * 页脚（可配置多条，用封号间隔）
     */
    @TableField("footers")
    private String footers;

    /**
     * 网站访问量
     */
    @TableField("view_count")
    private Long viewCount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除 【0：未删除；1：删除】
     */
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;


}
