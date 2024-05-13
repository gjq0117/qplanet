package com.gjq.planet.common.domain.vo.resp.user;

import com.gjq.planet.common.domain.entity.IpInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: gjq0117
 * @date: 2024/4/30 11:06
 * @description: 用户列表响应实体
 */
@ApiModel("用户列表响应实体")
@Data
public class UserListResp {

    /**
     * 主键
     */
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 性别【0:保密；1:男；2:女】
     */
    @ApiModelProperty("性别【0:保密；1:男；2:女】")
    private Integer gender;

    /**
     * 是否启用【0：否；1：是】
     */
    @ApiModelProperty("是否启用【0：否；1：是】")
    private Integer userStatus;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 简介
     */
    @ApiModelProperty("简介")
    private String introduction;

    /**
     * IP详情
     */
    @ApiModelProperty("IP详情")
    private IpInfo ipInfo;

    /**
     * 是否在线【0：否；1：是】
     */
    @ApiModelProperty("是否在线【0：否；1：是】")
    private Integer isActive;

    /**
     * 最后的上线时间
     */
    @ApiModelProperty("最后的上线时间")
    private Date lastActiveTime;

    /**
     * 用户类型【0：admin；1：管理员；2：普通用户】
     *
     * @see com.gjq.planet.common.enums.SystemRoleEnum
     */
    @ApiModelProperty("用户类型【0：admin；1：管理员；2：普通用户】")
    private Integer userType;

    /**
     * 账号创建时间
     */
    @ApiModelProperty("账号创建时间")
    private Date createTime;
}
