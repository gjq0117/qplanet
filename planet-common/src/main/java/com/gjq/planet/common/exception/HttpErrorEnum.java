package com.gjq.planet.common.exception;

import cn.hutool.http.ContentType;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.utils.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: gjq0117
 * @date: 2024/4/14 15:42
 * @description: http错误枚举
 */
@AllArgsConstructor
public enum HttpErrorEnum implements ErrorEnum {
    ACCESS_DENIED(401, "登录失效，请重新登录！"),
    NEED_ADMIN_ACCESS(402,"您没有管理员权限噢！"),
    ACCOUNT_FREEZE(403,"账号已被冻结！如有疑问，请联系站长")
    ;


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
        response.setContentType(ContentType.JSON.toString(StandardCharsets.UTF_8));
        response.getWriter().write(JsonUtils.toStr(ApiResult.fail(httpCode, msg)));
    }
}
