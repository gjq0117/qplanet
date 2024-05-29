package com.gjq.planet.common.domain.vo.resp.userfriend;

import com.gjq.planet.common.domain.vo.resp.user.UserSummerInfoResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author: gjq0117
 * @date: 2024/5/29 12:32
 * @description: 好友详情信息
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel("好友详情信息")
@Data
public class FriendDetailedResp extends UserSummerInfoResp {

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("是否在线")
    private Integer isActive;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否是特别关心")
    private Integer isCare;

}
