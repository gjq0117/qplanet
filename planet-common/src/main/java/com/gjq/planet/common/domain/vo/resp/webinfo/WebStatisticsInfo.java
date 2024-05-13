package com.gjq.planet.common.domain.vo.resp.webinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/4/24 21:38
 * @description: 网站统计信息
 */
@ApiModel("网站统计信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebStatisticsInfo {

    @ApiModelProperty("分类数量")
    private Integer sortCount;

    @ApiModelProperty("文章数量")
    private Integer articleCount;

    @ApiModelProperty("网站访问量")
    private Long visitCount;
}
