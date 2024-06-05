package com.gjq.planet.common.domain.dto.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/29 22:04
 * @description: 文本消息请求
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextMsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息内容")
    @NotBlank(message = "消息内容不能为空")
    @Size(max = 1024, message = "消息内容过长")
    private String content;

    @ApiModelProperty("回复的消息id,如果没有就别传")
    private Long replyMsgId;

    @ApiModelProperty("与回复的消息的间隔")
    private Integer skipCount;

    @ApiModelProperty("艾特的uid")
    @Size(max = 10, message = "一次别艾特这么多人噢~")
    private List<Long> atUidList;
}
