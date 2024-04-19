package com.gjq.planet.blog.exception;

import com.gjq.planet.blog.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: gjq0117
 * @date: 2024/4/13 16:32
 * @description: 全局异常处理去
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     *  参数校验异常
     *
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(x -> errorMsg.append(x.getField()).append(x.getDefaultMessage()).append(","));
        String message = errorMsg.toString();
        return ApiResult.fail(CommonErrorEnum.PARAM_INVALID.getCode(), message.substring(0, message.length() - 1));
    }

    /**
     *  业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ApiResult<?> businessException(BusinessException e) {
        log.info("business exception! The reason is : {}", e.getMessage());
        return ApiResult.fail(e.getErrorCode(), e.getMessage());
    }

    /**
     *  最后一道防线
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public ApiResult<?> throwable(Throwable e) {
        log.error("system exception! The reason is: {}", e.getMessage(), e);
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }
}
