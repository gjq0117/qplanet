package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.common.domain.dto.msg.ImgMsgDTO;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.enums.im.MessageTypeEnum;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:40
 * @description: 图片消息处理器
 */
public class ImgMsgHandler extends AbstractMsgHandler<ImgMsgDTO> {


    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.IMG;
    }

    @Override
    protected void checkMsg(ImgMsgDTO imgMsgDTO, Long roomId, Long uid) {

    }

    @Override
    protected void saveMsg(Message message, ImgMsgDTO body) {

    }

    @Override
    public Object showMsg(Message message) {
        return message.getExtra().getImgMsgDTO();
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[图片]";
    }
}
