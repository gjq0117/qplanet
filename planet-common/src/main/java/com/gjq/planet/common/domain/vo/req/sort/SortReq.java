package com.gjq.planet.common.domain.vo.req.sort;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * @author: gjq0117
 * @date: 2024/4/19 22:32
 * @description:
 */
@ApiModel("新增分类请求实体")
@Data
public class SortReq {

    @ApiModelProperty("id")
    private Long id;

    /**
     * 分类名
     */
    @ApiModelProperty("分类名")
    @NotBlank(message = "分类名不能为空")
    private String sortName;

    /**
     * 分类描述
     */
    @ApiModelProperty("分类描述")
    @NotBlank(message = "分类描述不能为空")
    private String sortDescription;

    /**
     * 背景图片
     */
    @NotBlank(message = "背景图片不能为空")
    @ApiModelProperty("背景图片")
    private String sortImg;

    /**
     * 格言
     */
    @ApiModelProperty("格言")
    private String motto;

    @ApiModelProperty("权重")
    @Min(value = 0, message = "权重不能小于0")
    private Integer priority;
}
