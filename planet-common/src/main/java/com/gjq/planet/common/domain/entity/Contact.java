package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会话信息表
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("contact")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Long uid;

    /**
     * 房间ID
     */
    @TableField("room_id")
    private Long roomId;

    /**
     * 阅读到的时间
     */
    @TableField("read_time")
    private Date readTime;

    /**
     * 会话内消息最后更新的时间(只有普通会话需要维护，全员会话不需要维护)
     */
    @TableField("active_time")
    private Date activeTime;

    /**
     * 最后一条消息ID
     */
    @TableField("last_msg_id")
    private Long lastMsgId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除【0：未删除；1：删除】
     */
    @TableLogic
    @TableField("deleted")
    private Boolean deleted;

}
