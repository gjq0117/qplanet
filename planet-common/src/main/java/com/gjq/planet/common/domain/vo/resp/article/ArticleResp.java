package com.gjq.planet.common.domain.vo.resp.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/4/22 16:41
 * @description: 文章信息响应实体
 */
@ApiModel("文章信息响应实体")
@Data
public class ArticleResp implements Serializable {

    @ApiModelProperty("文章ID")
    private Long id;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("分类名")
    private SortResp sortResp;

    @ApiModelProperty("标签列表")
    private List<LabelResp> labelList;

    @ApiModelProperty("浏览量")
    private Long viewCount;

    @ApiModelProperty("点赞量")
    private Long likeCount;

    @ApiModelProperty("文章状态【0：保存；1：发布；2：下架】")
    private Integer status;

    @ApiModelProperty("视频链接")
    private String videoUrl;

    @ApiModelProperty("文章内容")
    private String articleContent;

    @ApiModelProperty("评论量")
    private Long commentCount;

    @ApiModelProperty("封面")
    private String articleCover;

    @ApiModelProperty("是否可见")
    private Boolean viewStatus;

    @ApiModelProperty("是否启用评论")
    private Boolean commentStatus;

    @ApiModelProperty("是否推荐")
    private Boolean recommendStatus;

    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("最后修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
