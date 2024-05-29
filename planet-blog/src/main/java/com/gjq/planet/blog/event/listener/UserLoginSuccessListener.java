package com.gjq.planet.blog.event.listener;


import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.event.UserLoginSuccessEvent;
import com.gjq.planet.blog.service.IVisitorService;
import com.gjq.planet.blog.service.IpService;
import com.gjq.planet.common.utils.RequestHolder;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.enums.blog.UserActiveStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/4/14 14:40
 * @description: 用户登录成功事件监听器
 */
@Component
public class UserLoginSuccessListener {

    @Autowired
    private IpService ipService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private IVisitorService visitorService;


    /**
     * 用户上线后更新用户状态（最后上线时间/是否在线/ip）
     *
     * @param userLoginSuccessEvent
     */
    @EventListener(classes = UserLoginSuccessEvent.class)
    public void updateStatus(UserLoginSuccessEvent userLoginSuccessEvent) {
        User user = userLoginSuccessEvent.getUser();
        User update = User.builder()
                .id(user.getId())
                // 最后上线时间
                .lastActiveTime(new Date())
                // 在线状态
                .isActive(UserActiveStatusEnum.ONLINE.getStatus())
                .build();
        userDao.updateById(update);
        // 更新ip信息
        ipService.refreshIpInfoAsync(user.getId(), RequestHolder.get().getIp());
    }

    /**
     * 保存访问信息
     *
     * @param userLoginSuccessEvent
     */
    @EventListener(classes = UserLoginSuccessEvent.class)
    public void saveVisitInfo(UserLoginSuccessEvent userLoginSuccessEvent) {
        User user = userLoginSuccessEvent.getUser();
        String ip = Optional.ofNullable(RequestHolder.get().getIp()).orElse("127.0.0.1");
        visitorService.saveWebVisitInfo(user.getId(), ip);
    }
}
