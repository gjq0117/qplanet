package com.gjq.planet.blog.service.impl;

import cn.hutool.json.JSONUtil;
import com.gjq.planet.blog.service.WebsocketService;
import com.gjq.planet.blog.utils.TokenUtils;
import com.gjq.planet.blog.websocket.NettyUtil;
import com.gjq.planet.common.domain.vo.resp.websocket.WSResp;
import com.gjq.planet.common.domain.vo.resp.websocket.base.TokenValidFail;
import com.gjq.planet.common.domain.vo.resp.websocket.base.WSLoginSuccess;
import com.gjq.planet.common.domain.vo.resp.websocket.base.WsBaseResp;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

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

    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * 所有在线的用户和对应的socket
     */
    private static final ConcurrentHashMap<Long, Channel> ONLINE_UID_MAP = new ConcurrentHashMap<>();

    @Override
    public void authorize(Channel channel, String token) {
        // 跟Netty中的token对比
        String validToken = NettyUtil.getAttr(channel, NettyUtil.TOKEN);
        Long uidOrNull = tokenUtils.getUidOrNull(token);
        if (Objects.isNull(uidOrNull) || Objects.nonNull(token) && !token.equals(validToken)) {
            // 通知客户端token失效
            WsBaseResp baseResp = new TokenValidFail(token);
            sendMsg(channel, new WSResp(baseResp));
        } else {
            // 认证成功
            loginSuccess(channel, uidOrNull);
        }
    }

    @Override
    public void loginSuccess(Channel channel, Long uid) {
        // 保存连接信息
        if (Objects.isNull(ONLINE_UID_MAP.get(uid))) {
            ONLINE_UID_MAP.put(uid, channel);
        }
        WsBaseResp baseResp = new WSLoginSuccess(uid);
        // 发送登录成功消息
        sendMsg(channel, new WSResp(baseResp));
        // TODO 通知前端更新在线人数列表
    }


    @Override
    public void userOffline(Channel channel, String token) {
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

    @Override
    public void pushMsg(WsBaseResp wsBaseResp, List<Long> uidList) {
        WSResp wsResp = new WSResp(wsBaseResp);
        if (Objects.nonNull(uidList)) {
            // 推送指定用户
            for (Long uid : uidList) {
                Channel channel = ONLINE_UID_MAP.get(uid);
                if (Objects.nonNull(channel)) {
                    executor.execute(() -> {
                        sendMsg(channel, wsResp);
                    });
                }
            }
        } else {
            // 推送给所有人
            ONLINE_UID_MAP.values().forEach(channel -> {
                if (Objects.nonNull(channel)) {
                    executor.execute(() -> {
                        sendMsg(channel, wsResp);
                    });
                }
            });
        }
    }

}
