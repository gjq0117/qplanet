package com.gjq.planet.common.domain.vo.req.user;

import com.gjq.planet.common.valid.Gender;
import com.gjq.planet.common.valid.RegexpConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author: gjq0117
 * @date: 2024/4/29 17:26
 * @description: 修改用户信息请求实体
 */
@ApiModel("修改用户信息请求实体")
@Data
public class ModifyUserInfoReq {

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp = RegexpConstant.USERNAME_REGEXP, message = RegexpConstant.USERNAME_MESSAGE)
    private String username;

    @ApiModelProperty("手机号")
    @Pattern(regexp = RegexpConstant.PHONE_REGEXP, message = RegexpConstant.PHONE_MESSAGE)
    private String phone;

    @ApiModelProperty("昵称")
    @Pattern(regexp = RegexpConstant.NICKNAME_REGEXP, message = RegexpConstant.NICKNAME_MASSAGE)
    private String nickname;

    @ApiModelProperty("性别")
    @Gender
    private Integer gender;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("简介")
    private String introduction;
}
