package com.gjq.planet.common.domain.vo.resp.webinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/4/19 11:56
 * @description: 网站信息列表项
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("网站信息列表项")
public class WebInfoListItem {

    @ApiModelProperty("属性值")
    private String value;
}
