package com.gjq.planet.common.domain.vo.resp.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: gjq0117
 * @date: 2024/4/24 22:00
 * @description: 推荐文章信息
 */
@ApiModel("推荐文章信息")
@Data
public class RecommendArticleResp {

    @ApiModelProperty("文章ID")
    private Long id;

    @ApiModelProperty("文章封面")
    private String articleCover;

    @ApiModelProperty("是否有视频")
    private Boolean hasVideo;

    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("发布时间")
    private Date publishTime;
}
