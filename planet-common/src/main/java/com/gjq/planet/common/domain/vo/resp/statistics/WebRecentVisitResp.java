package com.gjq.planet.common.domain.vo.resp.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/5/4 14:06
 * @description: 网站近期访问量
 */
@ApiModel("网站近期访问量")
@Data
public class WebRecentVisitResp {

    @ApiModelProperty("日期")
    private String dates;

    @ApiModelProperty("日期对应访问量")
    private String visits;
}
