package com.gjq.planet.common.domain.vo.req.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/4/23 17:06
 * @description: 文章条件查询请求实体
 */
@ApiModel("文章条件查询请求实体")
@Data
public class SearchArticleReq {

    @ApiModelProperty("可见状态")
    private Boolean viewStatus;

    @ApiModelProperty("评论状态")
    private Boolean commentStatus;

    @ApiModelProperty("推荐状态")
    private Boolean recommendStatus;

    @ApiModelProperty("分类ID")
    private Long sortId;

    @ApiModelProperty("模糊查询Key")
    private String searchKey;
}
