package com.gjq.planet.common.domain.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/23 14:24
 * @description: 游标翻页返回实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("游标翻页返回实体")
public class CursorPageBaseResp<T> {

    @ApiModelProperty("游标（下次翻页带上这参数）")
    private String cursor;

    @ApiModelProperty("是否最后一页")
    private Boolean isLast = Boolean.FALSE;

    @ApiModelProperty("数据列表")
    private List<T> list;
}
