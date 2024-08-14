package com.gjq.planet.blog.interceptor;

import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.common.exception.HttpErrorEnum;
import com.gjq.planet.blog.service.IUserService;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/4/30 15:05
 * @description: 用户状态拦截器（主要用于拦截那些以及被封号的）
 */
@Component
public class UserStatusInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long uid = Optional.ofNullable(request.getAttribute(TokenInterceptor.UID)).map(Object::toString).map(Long::parseLong).orElse(null);
        if (Objects.isNull(uid)) {
            return true;
        }
        User user = userDao.getById(uid);
        if (YesOrNoEnum.NO.getCode().equals(user.getUserStatus())) {
            // 账号被冻结了
            if (YesOrNoEnum.YES.getCode().equals(user.getIsActive())) {
                // 如果还在线的话  则强制下线
                userService.logoutByUid(user.getId());

            }
            HttpErrorEnum.ACCOUNT_FREEZE.sendHttpError(response);
            return false;
        }
        return true;
    }
}
