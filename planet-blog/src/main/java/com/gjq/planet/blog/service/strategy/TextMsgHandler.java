package com.gjq.planet.blog.service.strategy;

import cn.hutool.core.collection.CollectionUtil;
import com.gjq.planet.blog.cache.redis.batch.GroupMemberCache;
import com.gjq.planet.blog.cache.redis.batch.RoomGroupCache;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.service.IRoleService;
import com.gjq.planet.common.domain.dto.msg.MessageExtra;
import com.gjq.planet.common.domain.dto.msg.TextMsgDTO;
import com.gjq.planet.common.domain.entity.GroupMember;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.domain.entity.RoomGroup;
import com.gjq.planet.common.domain.vo.resp.chat.TextMessageBody;
import com.gjq.planet.common.enums.im.MessageTypeEnum;
import com.gjq.planet.common.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:37
 * @description: 文本消息处理器
 */
@Component
public class TextMsgHandler extends AbstractMsgHandler<TextMsgDTO> {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private GroupMemberCache groupMemberCache;

    @Autowired
    private RoomGroupCache roomGroupCache;

    @Autowired
    private IRoleService roleService;

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.TEXT;
    }

    @Override
    protected void checkMsg(TextMsgDTO textMsgDTO, Long roomId, Long uid) {
        // 校验回复的消息
        if (Objects.nonNull(textMsgDTO.getReplyMsgId())) {
            // 判断被回复的消息是否存在
            Message message = messageDao.getById(textMsgDTO.getReplyMsgId());
            AssertUtil.isNotEmpty(message, "回复的消息不存在~");
            // 判断回复的消息是否在同一个会话里
            AssertUtil.isTrue(roomId.equals(message.getRoomId()), "只能回复相同会话的消息噢~");
        }
        // 校验@列表
        if (CollectionUtil.isNotEmpty(textMsgDTO.getAtUidList())) {
            // 前端可能传来重复的
            List<Long> atUidList = textMsgDTO.getAtUidList().stream().distinct().collect(Collectors.toList());
            RoomGroup roomGroup = roomGroupCache.get(roomId);
            List<GroupMember> memberCacheBatch = groupMemberCache.getBatch(roomGroup.getId(), atUidList);
            AssertUtil.equal(atUidList.size(), memberCacheBatch.size(), "@用户不存在");
            // 判断是否有@全体成员
            if (atUidList.contains(0L)) {
                GroupMember groupMember = groupMemberCache.get(roomGroup.getId(), uid);
                AssertUtil.isTrue(roleService.chatHasAtAll(groupMember), "你没有@全体成员的权限噢~");
            }
        }
    }

    @Override
    protected void saveMsg(Message message, TextMsgDTO body) {
        // 设置额外信息
        MessageExtra extra = Optional.ofNullable(message.getExtra()).orElse(new MessageExtra());
        extra.setTextMsgDTO(body);

        Message update = new Message();
        update.setId(message.getId());
        update.setExtra(extra);

        // 如果有回复消息
        if (Objects.nonNull(body.getReplyMsgId())) {
            update.setReplyMsgId(body.getReplyMsgId());
            // 计算与回复消息的间隔数
            Integer count = messageDao.getGapCount(message.getRoomId(), body.getReplyMsgId(), message.getId());
            body.setSkipCount(count);
        }
        messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message message) {
        TextMsgDTO textMsgDTO = message.getExtra().getTextMsgDTO();
        TextMessageBody textMessageBody = TextMessageBody.builder()
                .content(textMsgDTO.getContent())
                .atUidList(textMsgDTO.getAtUidList())
                .build();
        if (Objects.nonNull(textMsgDTO.getReplyMsgId())) {
            // 存在回复的消息
            Message dbReplyMsg = messageDao.getById(textMsgDTO.getReplyMsgId());
            if (Objects.nonNull(dbReplyMsg)) {
                TextMessageBody.ReplyMsg replyMsg = TextMessageBody.ReplyMsg.builder()
                        .id(dbReplyMsg.getId())
                        .uid(dbReplyMsg.getFromUid())
                        .type(dbReplyMsg.getType())
                        .canCallback(1)
                        .gapCount(textMsgDTO.getSkipCount())
                        .build();
                AbstractMsgHandler<?> msgHandler = MsgHandlerFactory.getStrategyNoNull(dbReplyMsg.getType());
                replyMsg.setBody(msgHandler.showMsg(dbReplyMsg));

                textMessageBody.setReply(replyMsg);
            }
        }
        return textMessageBody;
    }

    @Override
    public String showContactMsg(Message msg) {
        return msg.getExtra().getTextMsgDTO().getContent();
    }
}
