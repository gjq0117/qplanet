package com.gjq.planet.common.domain.vo.req.userapply;

import com.gjq.planet.common.enums.im.UserApplyTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author: gjq0117
 * @date: 2024/5/26 15:05
 * @description: 好友申请请求实体
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("好友申请请求实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendApplyReq extends UserApplyBaseReq {

    @ApiModelProperty("申请类型")
    private Integer type = UserApplyTypeEnum.FRIEND_APPLY.getType();

    @ApiModelProperty("目标id")
    @NotNull(message = "申请目标id不能为空")
    private Long targetId;
}
