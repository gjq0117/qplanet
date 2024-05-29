package com.gjq.planet.common.domain.vo.req.userapply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: gjq0117
 * @date: 2024/5/26 15:00
 * @description: 用户申请基础请求
 */
@ApiModel("用户申请基础请求")
@Data
public class UserApplyBaseReq {

    @ApiModelProperty("申请人id")
    @NotNull(message = "申请人不能为空")
    private Long uid;

    @ApiModelProperty("备注")
    private String remark;
}
