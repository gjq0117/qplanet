package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.common.domain.dto.msg.FileMsgDTO;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.enums.im.MessageTypeEnum;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:39
 * @description: 文件消息处理器
 */
public class FileMsgHandler extends AbstractMsgHandler<FileMsgDTO> {

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.FILE;
    }

    @Override
    protected void checkMsg(FileMsgDTO fileMsgDTO, Long roomId, Long uid) {

    }

    @Override
    protected void saveMsg(Message message, FileMsgDTO body) {

    }

    @Override
    public Object showMsg(Message message) {
        return message.getExtra().getFileMsgDTO();
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[文件]";
    }
}
