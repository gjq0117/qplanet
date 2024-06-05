package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gjq.planet.common.domain.dto.msg.MessageExtra;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "message", autoResultMap = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间ID
     */
    @TableField("room_id")
    private Long roomId;

    /**
     * 发送者ID
     */
    @TableField("from_uid")
    private Long fromUid;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 回复消息的ID
     */
    @TableField("reply_msg_id")
    private Long replyMsgId;

    /**
     * 跟回复的消息间隔了多少条消息
     */
    @TableField("skip_count")
    private Integer skipCount;

    /**
     * 已读人数
     */
    @TableField("read_count")
    private Integer readCount;

    /**
     * 类型
     *
     * @see com.gjq.planet.common.enums.im.MessageTypeEnum
     */
    @TableField("type")
    private Integer type;

    /**
     * 扩展信息
     */
    @TableField(value = "extra", typeHandler = JacksonTypeHandler.class)
    private MessageExtra extra;

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
