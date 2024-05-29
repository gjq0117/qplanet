package com.gjq.planet.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gjq.planet.common.enums.blog.SystemRoleEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "user", autoResultMap = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 性别【0:保密；1:男；2:女】
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 是否启用【0：否；1：是】
     */
    @TableField("user_status")
    private Integer userStatus;

    /**
     * openId
     */
    @TableField("open_id")
    private String openId;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * ip信息
     */
    @TableField(value = "ip_info", typeHandler = JacksonTypeHandler.class)
    private IpInfo ipInfo;

    /**
     * 是否在线【0：否；1：是】
     *
     * @see com.gjq.planet.common.enums.blog.UserActiveStatusEnum
     */
    @TableField("is_active")
    private Integer isActive;

    /**
     * 最后的上线时间
     */
    @TableField("last_active_time")
    private Date lastActiveTime;

    /**
     * 用户类型【0：admin；1：管理员；2：普通用户】
     *
     * @see SystemRoleEnum
     */
    @TableField("user_type")
    private Integer userType;

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
     * 是否删除【0：未删除；1：删除】
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
