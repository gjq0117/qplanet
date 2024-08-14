package com.gjq.planet.blog.interceptor;

import com.gjq.planet.common.annotation.PlanetAdmin;
import com.gjq.planet.common.exception.HttpErrorEnum;
import com.gjq.planet.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/4/23 12:18
 * @description: 管理员拦截器（用于拦截非法用户试图访问管理员权限接口）
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            // 判断方法上是否有@PlanetAdmin注解
            boolean present = method.getMethod().isAnnotationPresent(PlanetAdmin.class);
            if (!present) {
                return true;
            }
            // 有这个注解、校验用户是否拥有管理员权限
            Long uid = Optional.ofNullable(request.getAttribute(TokenInterceptor.UID)).map(Object::toString).map(Long::parseLong).orElse(null);
            Boolean isAdmin = userService.isAdmin(uid);
            if (!isAdmin) {
                // 没有管理员权限
                HttpErrorEnum.NEED_ADMIN_ACCESS.sendHttpError(response);
                return false;
            }
        }
        return true;
    }
}
