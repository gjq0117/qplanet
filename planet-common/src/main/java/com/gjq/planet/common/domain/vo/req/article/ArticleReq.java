package com.gjq.planet.common.domain.vo.req.article;

import com.gjq.planet.common.valid.group.InsertGroup;
import com.gjq.planet.common.valid.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/4/22 12:13
 * @description: 文章请求实体
 */
@ApiModel("文章请求实体")
@Data
public class ArticleReq {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("分类ID")
    @NotNull(message = "分类信息不能为空", groups = InsertGroup.class)
    @Min(value = 1, message = "分类信息不能为空", groups = InsertGroup.class)
    private Long sortId;

    @ApiModelProperty("选择的标签列表")
    @Size(min = 1, max = 3, message = "文章最少绑定一个标签，最多绑定三个标签", groups = InsertGroup.class)
    private List<Long> labelIdList;

    @ApiModelProperty("文章标题")
    @NotBlank(message = "文章标题不能为空", groups = {UpdateGroup.class, InsertGroup.class})
    private String articleTitle;

    @ApiModelProperty("视频链接")
    private String videoUrl;

    @ApiModelProperty("文章内容")
    @NotBlank(message = "文章内容不能为空", groups = InsertGroup.class)
    private String articleContent;

    @ApiModelProperty("是否启用评论")
    @NotNull(message = "是否启用评论不能为空", groups = InsertGroup.class)
    private Boolean commentStatus;

    @ApiModelProperty("是否推荐")
    @NotNull(message = "是否推荐不能为空", groups = InsertGroup.class)
    private Boolean recommendStatus;

    @ApiModelProperty("是否可见")
    @NotNull(message = "是否可见不能为空", groups = InsertGroup.class)
    private Boolean viewStatus;

    @ApiModelProperty("文章封面")
    @NotBlank(message = "文章封面不能为空", groups = InsertGroup.class)
    private String articleCover;

    @ApiModelProperty("文章状态 【0：保存；1：发布；2：下架】 （不需要添加）")
    @Null(message = "禁止手动填写文章状态")
    private Integer status;
}
