package com.gjq.planet.common.domain.vo.req.visitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/5/2 11:59
 * @description: 访问信息列表请求实体
 */
@ApiModel("访问信息列表请求实体")
@Data
public class VisitorListReq {

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("访问类型")
    private Integer type;

    @ApiModelProperty("用户名")
    private String username;
}
