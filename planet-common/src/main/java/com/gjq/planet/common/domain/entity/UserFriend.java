package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户好友表
 * </p>
 *
 * @author gjq
 * @since 2024-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_friend")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFriend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Long uid;

    /**
     * 好友ID
     */
    @TableField("friend_uid")
    private Long friendUid;

    /**
     * 是否特别关心【0：否、1：是】
     */
    @TableField("is_care")
    private Integer isCare;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

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
     * 是否删除【0：未删除、1：删除】
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
