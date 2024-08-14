package com.gjq.planet.common.domain.vo.req.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



/**
 * @author: gjq0117
 * @date: 2024/4/21 11:53
 * @description: 标签请求实体
 */
@ApiModel("标签信息请求实体")
@Data
public class LabelReq {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("所属分类ID")
    @NotNull(message = "分类Id不能为空")
    private Long sortId;

    @ApiModelProperty("标签名")
    @NotBlank(message = "标签名不能为空")
    private String labelName;

    @ApiModelProperty("标签描述")
    @NotBlank(message = "标签描述不能为空")
    private String labelDescription;
}
