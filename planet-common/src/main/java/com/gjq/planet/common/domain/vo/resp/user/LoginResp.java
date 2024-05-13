package com.gjq.planet.common.domain.vo.resp.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/4/23 12:48
 * @description: 用户登录返回实体
 */
@ApiModel("用户登录返回实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResp {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("角色Id")
    private Integer userType;
}
