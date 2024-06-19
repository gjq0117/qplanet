package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.resp.websocket.WSResp;
import com.gjq.planet.common.domain.vo.resp.websocket.base.WsBaseResp;
import io.netty.channel.Channel;

import java.util.Set;

/**
 * @author: gjq0117
 * @date: 2024/5/15 15:19
 * @description: Websocket业务处理
 */
public interface WebsocketService {

    /**
     * 认证
     *
     * @param channel 通道
     * @param token   数据(token)
     */
    void authorize(Channel channel, String token);

    /**
     * 登录成功
     *
     * @param channel 通道
     * @param uid     uid
     */
    void loginSuccess(Channel channel, Long uid);

    /**
     * 用户离线(token模式)
     *
     * @param channel 通道
     * @param token   用户token
     */
    void userOffline(Channel channel, String token);

    /**
     * 用户离线（uid模式）
     *
     * @param uid     用户ID
     */
    void userOffline(Long uid);

    /**
     * 发送消息给指定通道
     *
     * @param channel 通道
     * @param resp    消息
     */
    void sendMsg(Channel channel, WSResp resp);

    /**
     * 推送消息给指定用户
     *
     * @param wsBaseResp 新消息
     * @param uidSet     用户id列表(如果为null，就推送全体在线成员)
     */
    void pushMsg(WsBaseResp wsBaseResp, Set<Long> uidSet);
}
