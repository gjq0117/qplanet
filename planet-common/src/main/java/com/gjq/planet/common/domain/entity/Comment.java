package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 评论信息
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 发表用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 楼层ID
     */
    @TableField("floor_id")
    private Long floorId;

    /**
     * 父评论ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 父发表用户ID
     */
    @TableField("parent_user_id")
    private Long parentUserId;

    /**
     * 评论内容
     */
    @TableField("comment_content")
    private String commentContent;

    /**
     * 评论点赞数
     */
    @TableField("like_count")
    private Long likeCount;

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
