package com.gjq.planet.common.domain.vo.req.chat;

import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: gjq0117
 * @date: 2024/6/2 10:39
 * @description: 消息列表分页请求
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("消息列表分页请求")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagePageReq extends CursorPageBaseReq {

    @ApiModelProperty("房间号ID")
    @NotNull(message = "房间号不能为空")
    private Long roomId;
}
