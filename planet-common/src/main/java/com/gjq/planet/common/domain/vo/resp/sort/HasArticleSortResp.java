package com.gjq.planet.common.domain.vo.resp.sort;

import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/4/25 12:14
 * @description: 含有文章的分类信息
 */
@ApiModel("含有文章的分类信息")
@Data
public class HasArticleSortResp {

    @ApiModelProperty("分类ID")
    private Long id;

    @ApiModelProperty("分类名")
    private String sortName;

    @ApiModelProperty("分类优先级")
    private Integer priority;

    @ApiModelProperty("分类下的所有文章")
    private List<ArticleResp> articleRespList;
}
