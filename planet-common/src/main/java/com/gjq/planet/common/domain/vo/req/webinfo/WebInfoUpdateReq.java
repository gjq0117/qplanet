package com.gjq.planet.common.domain.vo.req.webinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;



/**
 * @author: gjq0117
 * @date: 2024/4/18 18:03
 * @description: 网站信息更新实体
 */
@ApiModel("网站信息更新实体")
@Data
public class WebInfoUpdateReq {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("网站名字")
    @NotBlank(message = "网站名字不能为空")
    private String webName;

    @ApiModelProperty("作者名字")
    @NotBlank(message = "作者名字不能为空")
    private String authorName;

    @ApiModelProperty("格言列表")
    @NotBlank(message = "格言列表不能为空")
    private String mottos;

    @ApiModelProperty("公告列表")
    private String notices;

    @ApiModelProperty("页脚列表")
    @NotBlank(message = "页脚列表不能为空")
    private String footers;

    @ApiModelProperty("头像")
    @NotBlank(message = "头像不能为空")
    private String avatar;

    @ApiModelProperty("背景")
    @NotBlank(message = "背景图片不能为空")
    private String backgroundImage;

}
