package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 群成员信息
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("group_member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 群成员
     */
    @TableId("id")
    private Long id;

    /**
     * 群ID
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Long uid;

    /**
     * 角色类型【0：普通成员；1：管理员；2：群主】
     *
     * @see com.gjq.planet.common.enums.im.ChatGroupRoleEnum
     */
    @TableField("role")
    private Integer role;

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
     * 是否删除【0：未删除；1：已删除】
     */
    @TableLogic
    @TableField("deleted")
    private Integer deleted;


}
