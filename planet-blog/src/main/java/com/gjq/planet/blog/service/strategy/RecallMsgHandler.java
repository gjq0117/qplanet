package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.common.domain.dto.msg.RecallMsgDTO;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.enums.im.MessageTypeEnum;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:41
 * @description: 撤回消息处理器
 */
public class RecallMsgHandler extends AbstractMsgHandler<RecallMsgDTO> {


    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.RECALL;
    }

    @Override
    protected void checkMsg(RecallMsgDTO recallMsgDTO, Long roomId, Long uid) {

    }

    @Override
    protected void saveMsg(Message message, RecallMsgDTO body) {

    }

    @Override
    public Object showMsg(Message message) {
        return message.getExtra().getRecallMsgDTO();
    }

    @Override
    public String showContactMsg(Message msg) {
        return "撤回了一条消息";
    }
}
