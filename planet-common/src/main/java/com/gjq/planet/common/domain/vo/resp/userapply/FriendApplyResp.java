package com.gjq.planet.common.domain.vo.resp.userapply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/5/26 19:33
 * @description: 用户申请响应实体
 */
@ApiModel("好友申请响应实体")
@Data
public class FriendApplyResp {

    @ApiModelProperty("申请记录id")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long uid;

    @ApiModelProperty("申请状态【0：待审核、1：已同意、2：已拒绝】")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;
}
