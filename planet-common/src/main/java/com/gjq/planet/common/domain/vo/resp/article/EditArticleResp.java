package com.gjq.planet.common.domain.vo.resp.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/4/23 19:27
 * @description: 编辑文章的响应实体
 */
@ApiModel("编辑文章的响应实体")
@Data
public class EditArticleResp {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("视频链接")
    private String videoUrl;

    @ApiModelProperty("文章内容")
    private String articleContent;

    @ApiModelProperty("是否开启评论")
    private Boolean commentStatus;

    @ApiModelProperty("是否推荐")
    private Boolean recommendStatus;

    @ApiModelProperty("是否可见")
    private Boolean viewStatus;

    @ApiModelProperty("封面")
    private String articleCover;

    @ApiModelProperty("分类ID")
    private Long sortId;

    @ApiModelProperty("标签ID列表")
    private List<Long> labelIdList;
}
