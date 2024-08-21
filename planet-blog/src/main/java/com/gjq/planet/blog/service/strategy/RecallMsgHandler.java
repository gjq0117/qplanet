package com.gjq.planet.blog.service.strategy;

import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.common.domain.dto.msg.MessageExtra;
import com.gjq.planet.common.domain.dto.msg.RecallMsgDTO;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.enums.im.MessageTypeEnum;
import com.gjq.planet.common.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:41
 * @description: 撤回消息处理器
 */
@Component
public class RecallMsgHandler extends AbstractMsgHandler<RecallMsgDTO> {

    /**
     *  最大的撤回时间（ms）
     */
    private static final Long MAX_RECALL_TIME = 2 * 60 * 1000L;

    @Autowired
    private MessageDao messageDao;

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.RECALL;
    }

    @Override
    protected void checkMsg(RecallMsgDTO recallMsgDTO, Long roomId, Long uid) {
        // 检查消息是否存在 | 检查消息是否属于本人 | 撤回的消息是否超过两分钟
        Message message = messageDao.getById(recallMsgDTO.getMsgId());
        AssertUtil.isNotEmpty(message, "撤回的消息不存在~");
        AssertUtil.isTrue(Objects.equals(uid, message.getFromUid()), "这条消息不是你发送的噢~");
        AssertUtil.isTrue(recallMsgDTO.getRecallTime().getTime() - message.getCreateTime().getTime() <= MAX_RECALL_TIME, "消息已经超过两分钟了噢~不能撤回啦");
    }

    @Override
    protected void saveMsg(Message message, RecallMsgDTO body) {
        // 保存 MessageExtra
        MessageExtra extra = Optional.ofNullable(message.getExtra()).orElse(new MessageExtra());
        extra.setRecallMsgDTO(body);
        message.setExtra(extra);
        messageDao.updateById(message);
        // 删除被撤回的消息
        messageDao.removeById(body.getMsgId());
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
