package com.gjq.planet.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/4/6 15:28
 * @description: 通用的错误
 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum implements ErrorEnum {

    BUSINESS_ERROR(0, "{0}"),

    SYSTEM_ERROR(-1, "系统出小差了，请稍后再试噢~"),

    PARAM_INVALID(-2, "参数校验失败"),
    ;

    private final Integer code;

    private final String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
