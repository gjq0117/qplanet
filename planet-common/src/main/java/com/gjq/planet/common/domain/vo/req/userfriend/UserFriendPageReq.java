package com.gjq.planet.common.domain.vo.req.userfriend;

import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author: gjq0117
 * @date: 2024/5/28 11:16
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("好友列表分页请求")
@Data
public class UserFriendPageReq extends CursorPageBaseReq {

    @ApiModelProperty("当前用户ID")
    @NotNull(message = "当前用户id不能为空")
    private Long currUid;
}
