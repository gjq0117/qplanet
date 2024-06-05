package com.gjq.planet.common.domain.vo.resp.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/30 13:38
 * @description: 聊天消息响应实体
 */
@ApiModel("聊天消息响应实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageBody {


    @ApiModelProperty("发送者信息")
    private FromUserInfo fromUser;

    @ApiModelProperty("消息详情")
    private MessageInfo messageInfo;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FromUserInfo {
        private Long uid;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageInfo {

        @ApiModelProperty("消息id")
        private Long id;

        @ApiModelProperty("房间id")
        private Long roomId;

        @ApiModelProperty("消息发送时间")
        private String sendTime;

        @ApiModelProperty("消息类型 1正常文本 2.撤回消息")
        private Integer type;

        @ApiModelProperty("消息内容不同的消息类型，内容体不同")
        private Object body;
    }
}
