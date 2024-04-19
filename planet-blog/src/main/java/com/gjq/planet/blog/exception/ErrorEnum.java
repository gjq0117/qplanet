package com.gjq.planet.blog.exception;

/**
 * @author: gjq0117
 * @date: 2024/4/13 16:41
 * @description: 异常枚举
 */
public interface ErrorEnum {
    /**
     *  获取错误码
     *
     * @return
     */
    Integer getErrorCode();

    /**
     *  获取错误消息
     *
     * @return
     */
    String getErrorMsg();
}
