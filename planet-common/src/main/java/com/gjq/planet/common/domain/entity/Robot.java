package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 机器人信息
 * </p>
 *
 * @author gjq
 * @since 2024-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("robot")
public class Robot implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *  对应的uid
     */
    @TableField("uid")
    private Long uid;

    /**
     *  大模型
     */
    @TableField("model")
    private String model;

    /**
     *  模型的温度（0~1直接）
     */
    @TableField("temperature")
    private Float temperature;

    /**
     *  api_key
     */
    @TableField("api_key")
    private String apiKey;

    /**
     *  base_url
     */
    @TableField("base_url")
    private String baseUrl;

    /**
     *  今日回答次数
     */
    @TableField("today_reply_num")
    private Integer todayReplyNum;

    /**
     *  总共回答次数
     */
    @TableField("total_reply_num")
    private Integer totalReplyNum;

    /**
     *  每日上限的回答次数
     */
    @TableField("daily_limit_num")
    private Integer dailyLimitNum;

    /**
     *  今日失败次数
     */
    @TableField("today_fail_num")
    private Integer todayFailNum;

    /**
     *  总共失败次数
     */
    @TableField("total_fail_num")
    private Integer totalFailNum;

    /**
     *  是否启用 【0：否；1：是】
     */
    @TableField("enabled")
    private Integer enabled;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除【0：未删除；1：删除】
     */
    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}
