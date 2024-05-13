package com.gjq.planet.common.domain.vo.req.user;

import com.gjq.planet.common.valid.RegexpConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * @author: gjq0117
 * @date: 2024/4/29 14:48
 * @description: 修改密码请求实体
 */
@Data
@ApiModel("修改密码请求实体")
public class ModifyPwdReq {

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不对")
    private String email;

    @ApiModelProperty("新密码")
    @Pattern(regexp = RegexpConstant.PASSWORD_REGEXP, message = RegexpConstant.PASSWORD_MESSAGE)
    private String newPwd;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("验证码的key")
    private String key;
}
