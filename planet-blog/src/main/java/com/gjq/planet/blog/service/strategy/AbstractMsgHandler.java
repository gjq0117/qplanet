package com.gjq.planet.blog.service.strategy;

import cn.hutool.core.bean.BeanUtil;
import com.gjq.planet.blog.dao.MessageDao;
import com.gjq.planet.blog.service.adapter.MessageBuilder;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessageReq;
import com.gjq.planet.common.enums.im.MessageTypeEnum;
import com.gjq.planet.common.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;

/**
 * @author: gjq0117
 * @date: 2024/5/30 10:28
 * @description: 消息处理器抽象类
 */
public abstract class AbstractMsgHandler<Req> {

    @Autowired
    private MessageDao messageDao;

    private Class<Req> bodyClass;

    @PostConstruct
    private void init() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.bodyClass = (Class<Req>) genericSuperclass.getActualTypeArguments()[0];
        MsgHandlerFactory.register(getMsgTypeEnum().getType(), this);
    }

    /**
     * 消息类型
     */
    abstract MessageTypeEnum getMsgTypeEnum();

    /**
     * 子类扩展消息校验
     *
     * @param req    req
     * @param roomId roomId
     * @param uid    uid
     */
    protected abstract void checkMsg(Req req, Long roomId, Long uid);

    /**
     * 保存消息
     */
    protected abstract void saveMsg(Message message, Req body);

    /**
     * 展示消息
     */
    public abstract Object showMsg(Message message);

    /**
     * 会话列表——展示的消息
     */
    public abstract String showContactMsg(Message msg);

    public Long checkAndSaveMsg(ChatMessageReq request, Long uid) {
        Req body = this.toBean(request.getBody());
        //统一校验
        AssertUtil.allCheckValidateThrow(body);
        // 子类扩展校验
        checkMsg(body, request.getRoomId(), uid);
        // 构建消息实体
        Message insert = MessageBuilder.buildMsg(request, uid);
        // 保存
        messageDao.save(insert);
        // 子类扩展保存
        saveMsg(insert, body);
        return insert.getId();
    }

    private Req toBean(Object body) {
        if (bodyClass.isAssignableFrom(body.getClass())) {
            return (Req) body;
        }
        return BeanUtil.toBean(body, bodyClass);
    }
}
