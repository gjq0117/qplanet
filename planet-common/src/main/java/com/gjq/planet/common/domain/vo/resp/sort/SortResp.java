package com.gjq.planet.common.domain.vo.resp.sort;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/4/20 11:31
 * @description: 分类信息返回实体
 */
@ApiModel("分类信息返回实体")
@Data
public class SortResp {

    /**
     * 主键
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 分类名
     */
    @ApiModelProperty("分类名")
    private String sortName;

    /**
     * 分类描述
     */
    @ApiModelProperty("分类描述")
    private String sortDescription;

    /**
     * 背景图片
     */
    @ApiModelProperty("背景图片")
    private String sortImg;

    /**
     * 格言
     */
    @ApiModelProperty("格言")
    private String motto;

    @ApiModelProperty("该分类下的标签数")
    private Integer labelNum;
    /**
     * 该分类下的文章数
     */
    @ApiModelProperty("该分类下的文章数")
    private Integer articleNum;

    /**
     * 优先级：数字小的在前面
     */
    @ApiModelProperty("优先级：数字小的在前面")
    private Integer priority;
}
