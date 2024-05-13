package com.gjq.planet.common.domain.vo.resp.visitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/2 11:26
 * @description: 访问者的省份信息
 */
@Data
@ApiModel("访问者的省份信息")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitorProvinceResp {

    @ApiModelProperty("省份名")
    private String name;
}
