package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.common.domain.dto.msg.ImgMsgDTO;
import com.gjq.planet.common.domain.dto.msg.MessageExtra;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.enums.im.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:40
 * @description: 图片消息处理器
 */
@Component
public class ImgMsgHandler extends AbstractMsgHandler<ImgMsgDTO> {

    @Autowired
    private MessageDao messageDao;


    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.IMG;
    }

    @Override
    protected void checkMsg(ImgMsgDTO imgMsgDTO, Long roomId, Long uid) {

    }

    @Override
    protected void saveMsg(Message message, ImgMsgDTO body) {
        // 设置额外信息
        MessageExtra extra = Optional.ofNullable(message.getExtra()).orElse(new MessageExtra());
        extra.setImgMsgDTO(body);
        message.setExtra(extra);
        messageDao.updateById(message);
    }

    @Override
    public Object showMsg(Message message) {
        return Optional.ofNullable(message.getExtra()).orElse(new MessageExtra()).getImgMsgDTO();
    }

    @Override
    public String showContactMsg(Message msg) {
        return "[图片]";
    }
}
