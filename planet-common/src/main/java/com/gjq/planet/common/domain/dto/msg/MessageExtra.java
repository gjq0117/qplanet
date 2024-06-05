package com.gjq.planet.common.domain.dto.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/5/29 22:35
 * @description: 消息扩展属性
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文本消息详情
     */
    private TextMsgDTO textMsgDTO;

    /**
     * 图片消息详情
     */
    private ImgMsgDTO imgMsgDTO;

    /**
     * 表情包消息详情
     */
    private EmojiMsgDTO emojiMsgDTO;

    /**
     * 文件消息详情
     */
    private FileMsgDTO fileMsgDTO;

    /**
     * 撤回消息详情
     */
    private RecallMsgDTO recallMsgDTO;

    /**
     *  语音消息
     */
    private SoundMsgDTO soundMsgDTO;
}
