package com.gjq.planet.common.domain.vo.req.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: gjq0117
 * @date: 2024/5/29 21:56
 * @description: 消息发送请求体
 */
@ApiModel("消息发送请求体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageReq {

    @ApiModelProperty("房间号")
    @NotNull(message = "房间号不能为空")
    private Long roomId;

    /**
     * @see com.gjq.planet.common.enums.im.MessageTypeEnum
     */
    @ApiModelProperty("消息类型")
    @NotNull(message = "消息类型不能为空")
    private Integer msgType;

    /**
     * @see com.gjq.planet.common.domain.dto.msg
     */
    @ApiModelProperty("消息体，类型不同传值不同")
    @NotNull(message = "消息体不能为空")
    private Object body;
}
