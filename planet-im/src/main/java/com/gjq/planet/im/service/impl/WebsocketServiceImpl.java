package com.gjq.planet.im.service.impl;

import cn.hutool.json.JSONUtil;
import com.gjq.planet.common.domain.vo.resp.websocket.WSResp;
import com.gjq.planet.common.domain.vo.resp.websocket.base.BaseResp;
import com.gjq.planet.common.domain.vo.resp.websocket.base.TokenValidFail;
import com.gjq.planet.common.domain.vo.resp.websocket.base.WSLoginSuccess;
import com.gjq.planet.im.service.WebsocketService;
import com.gjq.planet.im.utils.TokenUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: gjq0117
 * @date: 2024/5/15 15:19
 * @description:
 */
@Service
@Slf4j
public class WebsocketServiceImpl implements WebsocketService {

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 所有在线的用户和对应的socket
     */
    private static final ConcurrentHashMap<Long, Channel> ONLINE_UID_MAP = new ConcurrentHashMap<>();

    @Override
    public void authorize(Channel channel, String token) {
        //1、验证token并取到uid
        Long uidOrNull = tokenUtils.getUidOrNull(token);
        if (Objects.nonNull(uidOrNull)) {
            // 认证成功
            loginSuccess(channel, uidOrNull);
        } else {
            // 通知客户端token失效
            BaseResp baseResp = new TokenValidFail().buildResp(token);
            sendMsg(channel, new WSResp(baseResp));
        }
    }

    @Override
    public void loginSuccess(Channel channel, Long uid) {
        // 保存连接信息
        if (Objects.isNull(ONLINE_UID_MAP.get(uid))) {
            ONLINE_UID_MAP.put(uid,channel);
        }
        BaseResp baseResp = new WSLoginSuccess().buildResp(uid);
        // 发送登录成功消息
        sendMsg(channel, new WSResp(baseResp));
        // TODO 通知前端更新在线人数列表
    }


    @Override
    public void userOffline(Channel channel,String token) {
        Long uidOrNull = tokenUtils.getUidOrNull(token);
        if (Objects.nonNull(uidOrNull)) {
            // 移除连接信息
            ONLINE_UID_MAP.remove(uidOrNull);
        }
    }

    @Override
    public void sendMsg(Channel channel, WSResp resp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(resp)));
    }

}
