package com.gjq.planet.blog.utils;

import com.gjq.planet.common.domain.dto.RequestInfo;

/**
 * @author: gjq0117
 * @date: 2024/4/14 14:50
 * @description: 请求上下文
 */
public class RequestHolder {

    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();

    /**
     * 设置请求信息
     *
     * @param requestInfo
     */
    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    /**
     * 获取请求信息
     *
     * @return
     */
    public static RequestInfo get() {
        return threadLocal.get();
    }

    /**
     * 移除请求信息
     */
    public static void remove() {
        threadLocal.remove();
    }
}
