package com.gjq.planet.blog.interceptor;

import com.gjq.planet.blog.annotation.NotToken;
import com.gjq.planet.blog.exception.HttpErrorEnum;
import com.gjq.planet.blog.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/4/14 14:58
 * @description: token过滤器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    public static final String UID = "uid";

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            // 判断方法上是否有@NotToken注解
            boolean notToken = method.getMethod().isAnnotationPresent(NotToken.class);
            // 不需要token，直接返回
            if (notToken) {
                return true;
            }
            Long uid = tokenUtils.getUidOrNull(request);
            if (Objects.isNull(uid)) {
                // 用户没有登录态
                HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
                return false;
            }
            request.setAttribute(UID, uid);
        }
        return true;
    }
}
