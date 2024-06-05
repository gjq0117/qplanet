package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.common.domain.dto.msg.SoundMsgDTO;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.enums.im.MessageTypeEnum;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:42
 * @description: 语音消息处理器
 */
public class SoundMsgHandler extends AbstractMsgHandler<SoundMsgDTO> {

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.SOUND;
    }

    @Override
    protected void checkMsg(SoundMsgDTO soundMsgDTO, Long roomId, Long uid) {

    }

    @Override
    protected void saveMsg(Message message, SoundMsgDTO body) {

    }

    @Override
    public Object showMsg(Message message) {
        return message.getExtra().getSoundMsgDTO();
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[语音]";
    }
}
