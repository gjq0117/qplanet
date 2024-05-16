package com.gjq.planet.im.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/5/15 18:09
 * @description:
 */
public class MyHeaderCollectHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.getUri());
            Optional<String> tokenOptional = Optional.of(urlBuilder)
                    .map(UrlBuilder::getQuery)
                    .map(k -> k.get("token"))
                    .map(CharSequence::toString);
            // 如果token存在
            tokenOptional.ifPresent(s -> NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, s));
            request.setUri(urlBuilder.getPath().toString());
            // 获取用户ip
            String ip = request.headers().get("X-Real-IP");
            if (StringUtils.isBlank(ip)) {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                ip = address.getAddress().getHostAddress();
            }
            // 保存到channel附件
            NettyUtil.setAttr(ctx.channel(), NettyUtil.IP, ip);
            // 处理器只需要用一次
            ctx.pipeline().remove(this);
        }
        ctx.fireChannelRead(msg);

    }
}
