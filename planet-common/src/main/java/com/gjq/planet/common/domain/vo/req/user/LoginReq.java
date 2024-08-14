package com.gjq.planet.common.domain.vo.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * @author: gjq0117
 * @date: 2024/4/13 16:14
 * @description: 管理员登录请求实体
 */

@Data
@ApiModel("用户登录请求实体")
public class LoginReq {

    @ApiModelProperty("登录账户，可以为用户名、手机号、邮箱")
    @NotBlank
    private String account;

    @ApiModelProperty("登录密码")
    @NotBlank
    private String password;
}
