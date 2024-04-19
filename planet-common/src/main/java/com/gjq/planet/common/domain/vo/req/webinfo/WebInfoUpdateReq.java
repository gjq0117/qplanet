package com.gjq.planet.common.domain.vo.req.webinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author: gjq0117
 * @date: 2024/4/18 18:03
 * @description: 网站信息更新实体
 */
@ApiModel("网站信息更新实体")
@Data
public class WebInfoUpdateReq {

    @NotNull
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("网站名字")
    private String webName;

    @ApiModelProperty("作者名字")
    private String authorName;

    @ApiModelProperty("格言列表")
    private String mottos;

    @ApiModelProperty("公告列表")
    private String notices;

    @ApiModelProperty("页脚列表")
    private String footers;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("背景")
    private String backgroundImage;

}
