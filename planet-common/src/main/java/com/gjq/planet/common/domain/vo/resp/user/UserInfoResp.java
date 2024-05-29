package com.gjq.planet.common.domain.vo.resp.user;

import com.gjq.planet.common.domain.entity.IpInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/4/29 13:41
 * @description: 用户登录后拿到的用户信息
 */
@ApiModel("用户登录后拿到的用户信息")
@Data
public class UserInfoResp {

    @ApiModelProperty("uid")
    private Long uid;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户类型")
    private Integer userType;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("Ip信息")
    private IpInfo ipInfo;

    @ApiModelProperty("在线状态")
    private Integer isActive;
}

