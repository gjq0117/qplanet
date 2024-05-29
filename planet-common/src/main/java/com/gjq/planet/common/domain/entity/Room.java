package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 房间信息表
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("room")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("id")
    private Long id;

    /**
     * 类型【1：单聊；2：普通群聊,3：全员群聊】
     *
     * @see com.gjq.planet.common.enums.im.RoomTypeEnum
     */
    @TableField("type")
    private Integer type;

    /**
     * 是否是热点群聊【0：否；1：是】
     */
    @TableField("hot_flag")
    private Integer hotFlag;

    /**
     * 群最后的消息时间
     */
    @TableField("active_time")
    private Date activeTime;

    /**
     * 最后一条消息的ID
     */
    @TableField("last_msg_id")
    private Long lastMsgId;

    /**
     * 扩展消息
     */
    @TableField("extra")
    private String extra;

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
