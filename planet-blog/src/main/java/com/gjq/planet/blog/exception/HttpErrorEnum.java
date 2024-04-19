package com.gjq.planet.blog.exception;

import cn.hutool.http.ContentType;
import com.gjq.planet.blog.utils.ApiResult;
import com.gjq.planet.blog.utils.JsonUtils;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: gjq0117
 * @date: 2024/4/14 15:42
 * @description: http错误枚举
 */
@AllArgsConstructor
public enum HttpErrorEnum implements ErrorEnum {
    ACCESS_DENIED(401, "登录失效，请重新登录");


    private Integer httpCode;
    private String msg;

    @Override
    public Integer getErrorCode() {
        return httpCode;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }

    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(httpCode);
        response.setContentType(ContentType.JSON.toString(StandardCharsets.UTF_8));
        response.getWriter().write(JsonUtils.toStr(ApiResult.fail(httpCode, msg)));
    }
}
