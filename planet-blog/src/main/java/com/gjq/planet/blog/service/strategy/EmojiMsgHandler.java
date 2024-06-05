package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.common.domain.dto.msg.EmojiMsgDTO;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.enums.im.MessageTypeEnum;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:40
 * @description: 表情包消息处理器
 */
public class EmojiMsgHandler extends AbstractMsgHandler<EmojiMsgDTO> {

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.EMOJI;
    }

    @Override
    protected void checkMsg(EmojiMsgDTO emojiMsgDTO, Long roomId, Long uid) {

    }

    @Override
    protected void saveMsg(Message message, EmojiMsgDTO body) {

    }

    @Override
    public Object showMsg(Message message) {
        return message.getExtra().getEmojiMsgDTO();
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[表情包]";
    }
}
