package com.gjq.planet.blog.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: gjq0117
 * @date: 2024/4/6 15:46
 * @description: 全局业务异常
 *
 */
@Getter
@Slf4j
public class BusinessException extends RuntimeException {

    protected Integer errorCode;

    protected String errorMsg;

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorCode = CommonErrorEnum.BUSINESS_ERROR.getErrorCode();
        this.errorMsg = errorMsg;
        log.error("系统业务异常：" + errorMsg);
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(ErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorCode = errorEnum.getErrorCode();
        this.errorMsg = errorEnum.getErrorMsg();
    }
}
