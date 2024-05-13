package com.gjq.planet.common.domain.vo.req.article;

import com.gjq.planet.common.enums.ArticleAttributeStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/4/23 14:52
 * @description: 改变文章状态请求实体
 */
@ApiOperation("改变文章状态请求实体")
@Data
public class ChangeStatusReq {

    @ApiModelProperty("文章ID")
    private Long articleId;

    @ApiModelProperty("状态")
    private Boolean status;

    /**
     * @see ArticleAttributeStatusEnum
     */
    @ApiModelProperty("类型")
    private Integer type;
}
