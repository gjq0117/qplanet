package com.gjq.planet.common.domain.vo.resp.label;

import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/4/21 12:06
 * @description: 标签信息返回实体
 */
@ApiModel("标签信息返回实体")
@Data
public class LabelResp implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("所属分类信息")
    private SortResp sortResp;

    @ApiModelProperty("标签下的文章数")
    private Integer articleNum;

    @ApiModelProperty("标签名")
    private String labelName;

    @ApiModelProperty("标签描述")
    private String labelDescription;

}
