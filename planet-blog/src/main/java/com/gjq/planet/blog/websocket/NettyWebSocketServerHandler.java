package com.gjq.planet.blog.websocket;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.gjq.planet.blog.service.WebsocketService;
import com.gjq.planet.common.domain.vo.req.websocket.WSBaseReq;
import com.gjq.planet.common.enums.im.WSReqTypeEnum;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: gjq0117
 * @date: 2024/5/15 15:04
 * @description: websocket业务处理
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebsocketService webSocketService;

    /**
     * 客户端连接后触发此方法
     *
     * @param ctx ctx
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        webSocketService = SpringUtil.getBean(WebsocketService.class);
    }

    /**
     * 当客户端断开的时候调用此方法
     *
     * @param ctx ctx
     * @throws Exception e
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 处理用户离线方法
        userOffLine(ctx);
    }

    /**
     * 取消绑定
     *
     * @param ctx ctx
     * @throws Exception e
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 可能出现业务判断离线后再次触发 channelInactive
        log.warn("触发 channelInactive 掉线![{}]", ctx.channel().id());
        userOffLine(ctx);
    }

    /**
     * 用户离线
     */
    private void userOffLine(ChannelHandlerContext ctx) {
        webSocketService.userOffline(ctx.channel(),NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN));
    }

    /**
     * 心跳检查
     *
     * @param ctx ctx
     * @param evt evt
     * @throws Exception e
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 读空闲
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                // 检测到没有用户的心跳 关闭用户的连接
                userOffLine(ctx);
            }
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            String token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
            if (StringUtils.isNotBlank(token)) {
                this.webSocketService.authorize(ctx.channel(), token);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq baseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(baseReq.getType())) {
            case HEARTBEAT:
                // 心跳检查
                break;
            case AUTHORIZE:
                // 认证
                webSocketService.authorize(ctx.channel(), baseReq.getData());
                break;
            default:
                log.error("未知消息类型,type:{},data:{}", baseReq.getType(), baseReq.getData());
        }
    }
}
