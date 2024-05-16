package com.gjq.planet.common.domain.vo.req.user;

import com.gjq.planet.common.enums.blog.GenderEnum;
import com.gjq.planet.common.enums.blog.SystemRoleEnum;
import com.gjq.planet.common.enums.blog.YesOrNoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/4/30 11:14
 * @description: 用户列表请求实体
 */
@ApiModel("用户列表请求实体")
@Data
public class UserListReq {

    /**
     * @see SystemRoleEnum
     */
    @ApiModelProperty("用户类型【0：admin；1：管理员；2：普通用户】")
    private Integer userType;

    /**
     * @see YesOrNoEnum
     */
    @ApiModelProperty("是否在线【0：否；1：是】")
    private Integer isActive;

    /**
     * @see YesOrNoEnum
     */
    @ApiModelProperty("是否启用【0：否；1：是】")
    private Integer userStatus;

    /**
     * @see GenderEnum
     */
    @ApiModelProperty("性别【0:保密；1:男；2:女】")
    private Integer gender;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户名")
    private String username;
}
