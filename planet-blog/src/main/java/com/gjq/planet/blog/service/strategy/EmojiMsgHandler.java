package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.common.domain.dto.msg.EmojiMsgDTO;
import com.gjq.planet.common.domain.dto.msg.MessageExtra;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.enums.im.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:40
 * @description: 表情包消息处理器
 */
@Component
public class EmojiMsgHandler extends AbstractMsgHandler<EmojiMsgDTO> {

    @Autowired
    private MessageDao messageDao;

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.EMOJI;
    }

    @Override
    protected void checkMsg(EmojiMsgDTO emojiMsgDTO, Long roomId, Long uid) {

    }

    @Override
    protected void saveMsg(Message message, EmojiMsgDTO body) {
        // 设置额外信息
        MessageExtra extra = Optional.ofNullable(message.getExtra()).orElse(new MessageExtra());
        extra.setEmojiMsgDTO(body);
        message.setExtra(extra);
        messageDao.updateById(message);
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
