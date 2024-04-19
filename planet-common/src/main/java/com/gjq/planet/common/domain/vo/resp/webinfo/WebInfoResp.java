package com.gjq.planet.common.domain.vo.resp.webinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/4/18 17:20
 * @description: 网站信息实体
 */
@ApiModel("网站信息实体")
@Data
public class WebInfoResp {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("网站名字")
    private String webName;

    @ApiModelProperty("作者名字")
    private String authorName;

    @ApiModelProperty("格言列表")
    private List<WebInfoListItem> mottoList;

    @ApiModelProperty("公告列表")
    private List<WebInfoListItem> noticeList;

    @ApiModelProperty("页脚列表")
    private List<WebInfoListItem> footerList;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("背景")
    private String backgroundImage;

    @ApiModelProperty("网站访问量")
    private Long viewCount;
}
