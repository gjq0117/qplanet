package com.gjq.planet.common.domain.vo.resp.chat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/6/1 16:03
 * @description: 文本消息响应体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageBody {

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("艾特的uid")
    private List<Long> atUidList;

    @ApiModelProperty("父消息，如果没有父消息，返回的是null")
    private TextMessageBody.ReplyMsg reply;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReplyMsg {
        @ApiModelProperty("消息id")
        private Long id;
        @ApiModelProperty("用户uid")
        private Long uid;
        @ApiModelProperty("消息类型")
        private Integer type;
        @ApiModelProperty("消息内容不同的消息类型，见父消息内容体")
        private Object body;
        @ApiModelProperty("是否可消息跳转 0否 1是")
        private Integer canCallback;
        @ApiModelProperty("跳转间隔的消息条数")
        private Integer gapCount;
    }
}
