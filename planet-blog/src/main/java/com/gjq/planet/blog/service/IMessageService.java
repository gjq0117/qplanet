package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.chat.ChatMessagePageReq;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.chat.ChatMessageBody;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
public interface IMessageService  {

    /**
     *  发送消息
     *
     * @param uid 发送者UID
     * @param req 消息请求体
     * @return 消息ID
     */
    Long sendMsg(Long uid, ChatMessageReq req);

    /**
     *  构建消息响应实体
     *
     * @param msgId 消息ID
     * @return ChatMessageResp
     */
    ChatMessageBody buildMsgResp(Long msgId);

    /**
     *  聊天消息列表分页请求
     *
     * @param req req
     * @param currId 当前用户ID
     * @return
     */
    CursorPageBaseResp<ChatMessageBody> PageMsg(ChatMessagePageReq req, Long currId);
}
