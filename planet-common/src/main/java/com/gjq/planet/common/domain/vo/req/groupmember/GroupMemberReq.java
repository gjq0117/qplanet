package com.gjq.planet.common.domain.vo.req.groupmember;

import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: gjq0117
 * @date: 2024/5/24 20:53
 * @description: 群成员请求实体
 */
@ApiModel("群成员请求实体")
@Data
public class GroupMemberReq extends CursorPageBaseReq {

    @ApiModelProperty("房间号")
    @NotNull(message = "房间号不能为空")
    private Long roomId;
}
