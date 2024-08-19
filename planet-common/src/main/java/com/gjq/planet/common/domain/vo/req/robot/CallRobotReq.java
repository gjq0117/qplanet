package com.gjq.planet.common.domain.vo.req.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: gjq0117
 * @date: 2024/8/15 22:36
 * @description: 回调机器人请求实体
 */
@ApiModel("回调机器人请求实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallRobotReq {

    @ApiModelProperty("回调机器人的ID")
    @NotNull(message = "robotId不能为空")
    private Long robotId;

    @ApiModelProperty("房间号")
    @NotNull(message = "房间号不能为空")
    private Long roomId;

    @ApiModelProperty("机器人需要回复的消息Id")
    @NotNull(message = "replyMsgId不能为空")
    private Long replyMsgId;
}
