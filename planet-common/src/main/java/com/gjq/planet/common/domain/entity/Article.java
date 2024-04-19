package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文章信息
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 作者ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 分类ID
     */
    @TableField("sort_id")
    private Long sortId;

    /**
     * 文章封面
     */
    @TableField("article_cover")
    private String articleCover;

    /**
     * 文章标题
     */
    @TableField("article_title")
    private String articleTitle;

    /**
     * 文章内容
     */
    @TableField("article_content")
    private String articleContent;

    /**
     * 视频链接
     */
    @TableField("video_url")
    private String videoUrl;

    /**
     * 浏览量
     */
    @TableField("view_count")
    private Long viewCount;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Long likeCount;

    /**
     * 是否可见【0：否；1：是】
     */
    @TableField("view_status")
    private Boolean viewStatus;

    /**
     * 是否推荐【0：否；1：是】
     */
    @TableField("recommend_status")
    private Boolean recommendStatus;

    /**
     * 是否启用评论【0：否；1：是】
     */
    @TableField("comment_status")
    private Boolean commentStatus;

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
    @TableLogic
    @TableField("deleted")
    private Boolean deleted;


}
