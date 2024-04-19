package com.gjq.planet.common.domain.vo.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: gjq0117
 * @date: 2024/4/13 16:14
 * @description: 管理员登录请求实体
 */

@Data
@ApiModel("管理员登录请求实体")
public class AdminLoginReq {

    @ApiModelProperty("登录账户，可以为用户名、手机号、邮箱")
    @NotBlank
    private String account;

    @ApiModelProperty("登录密码")
    private String password;
}
