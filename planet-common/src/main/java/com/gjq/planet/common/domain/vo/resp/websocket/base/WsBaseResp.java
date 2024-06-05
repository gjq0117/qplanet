package com.gjq.planet.common.domain.vo.resp.websocket.base;

/**
 * @author: gjq0117
 * @date: 2024/5/15 19:00
 * @description: ws响应实体基类
 */
public abstract class WsBaseResp {

    /**
     *  获取响应类型
     *
     * @return type
     */
    public abstract Integer getType();
}
