package com.gjq.planet.common.domain.vo.resp.visitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: gjq0117
 * @date: 2024/5/2 11:48
 * @description: 访问信息列表
 */
@ApiModel("访问信息列表")
@Data
public class VisitorListResp {

    @ApiModelProperty("访问用户名")
    private String username;

    @ApiModelProperty("访问IP")
    private String ip;

    @ApiModelProperty("国家")
    private String nation;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("访问类型")
    private Integer resourceType;

    @ApiModelProperty("访问资源名")
    private String resourceName;

    @ApiModelProperty("访问时间")
    private Date createTime;

}
