package com.gjq.planet.blog.event.listener;

import com.gjq.planet.blog.cache.redis.batch.RoomCache;
import com.gjq.planet.blog.config.openai.OpenAiFactory;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.event.MessageSendEvent;
import com.gjq.planet.blog.mq.producer.MqProducer;
import com.gjq.planet.blog.service.WebsocketService;
import com.gjq.planet.common.constant.MQConstant;
import com.gjq.planet.common.domain.dto.msg.MsgSendMessageDTO;
import com.gjq.planet.common.domain.dto.msg.TextMsgDTO;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.domain.entity.Room;
import com.gjq.planet.common.domain.vo.resp.websocket.base.CallRobot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: gjq0117
 * @date: 2024/6/1 11:06
 * @description: 消息发送事件监听器
 */
@Slf4j
@Component
public class MessageSendListener {

    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private WebsocketService websocketService;

    @Autowired
    private ThreadPoolExecutor executor;

    /**
     *  消息推送MQ
     *
     * @param event　event
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEvent.class, fallbackExecution = true)
    public void messageRoute(MessageSendEvent event) {
        Long msgId = event.getMsgId();
        // MQ生产者发送消息
        mqProducer.sendSecureMsg(MQConstant.SEND_MSG_TOPIC, new MsgSendMessageDTO(msgId), msgId);
    }


    /**
     *  判断是否需要call机器人，需要的话就通过ws推送一条消息给客户端，客户端手动call
     *
     * @param event event
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEvent.class, fallbackExecution = true)
    public void callRobot(MessageSendEvent event) {
        // 异步调用
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Long msgId = event.getMsgId();
                Message msg = messageDao.getById(msgId);
                Long robotId = needCallRobot(msg);
                if (Objects.nonNull(robotId)) {
                    websocketService.pushMsg(new CallRobot(robotId, msgId, msg.getRoomId()), Collections.singleton(msg.getFromUid()));
                }
            }
        });
    }

    /**
     *  判断是否需要call机器人,需要的话直接返回需要call的机器人的ID
     *
     * @param msg
     * @return robotId
     */
    private Long needCallRobot(Message msg) {
        Long result = null;
        // 单聊
        Room room = roomCache.get(msg.getRoomId());
        if (room.isRobotRoom()) {
            result = OpenAiFactory.getFirstModelId();
        } else {
            // 群聊(文本消息)
            TextMsgDTO textMsgDTO = msg.getExtra().getTextMsgDTO();
            for(Long id: textMsgDTO.getAtUidList()) {
                if (Objects.nonNull(OpenAiFactory.getModel(id))) {
                    result = id;
                    break;
                }
            }
        }
        return result;
    }
}
