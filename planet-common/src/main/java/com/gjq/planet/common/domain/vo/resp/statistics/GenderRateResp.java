package com.gjq.planet.common.domain.vo.resp.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/5/4 15:09
 * @description: 统计-男女比例
 */
@ApiModel("统计-男女比例")
@Data
public class GenderRateResp {

    @ApiModelProperty("类型（男、女、未知）")
    private String name;

    @ApiModelProperty("数量")
    private Integer value;
}
