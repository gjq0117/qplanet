package com.gjq.planet.common.domain.vo.resp.sort;

import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/4/27 16:33
 * @description: 分类页面响应实体
 */
@ApiModel("分类页面响应实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortPageResp {

    @ApiModelProperty("分类信息")
    private SortResp sortResp;

    @ApiModelProperty("标签列表")
    private List<LabelResp> labelResp;

    @ApiModelProperty("文章列表")
    private List<ArticleResp> articleRespList;

}
