package com.gjq.planet.im.service;

import com.gjq.planet.common.domain.vo.resp.websocket.WSResp;
import io.netty.channel.Channel;

/**
 * @author: gjq0117
 * @date: 2024/5/15 15:19
 * @description: Websocket业务处理
 */
public interface WebsocketService {

    /**
     *  认证
     *
     * @param channel 通道
     * @param token 数据(token)
     */
    void authorize(Channel channel, String token);

    /**
     *  登录成功
     *
     * @param channel 通道
     * @param uid uid
     */
    void loginSuccess(Channel channel, Long uid);

    /**
     *  用户离线
     *
     * @param channel 通道
     */
    void userOffline(Channel channel,String token);

    /**
     *  发送消息
     *
     * @param channel 通道
     * @param resp 消息
     */
    void sendMsg(Channel channel, WSResp resp);
}
