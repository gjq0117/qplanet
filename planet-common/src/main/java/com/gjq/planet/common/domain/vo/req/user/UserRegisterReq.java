package com.gjq.planet.common.domain.vo.req.user;

import com.gjq.planet.common.valid.RegexpConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author: gjq0117
 * @date: 2024/4/28 16:24
 * @description: 用户注册实体
 */
@ApiModel("用户注册实体")
@Data
public class UserRegisterReq {

    @ApiModelProperty("用户名")
    @Pattern(regexp = RegexpConstant.USERNAME_REGEXP, message = RegexpConstant.USERNAME_MESSAGE)
    private String username;

    @ApiModelProperty("密码")
    @Pattern(regexp = RegexpConstant.PASSWORD_REGEXP, message = RegexpConstant.PASSWORD_MESSAGE)
    private String password;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不对")
    private String email;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("验证码对应的key")
    private String key;
}
