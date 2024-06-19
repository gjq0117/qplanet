package com.gjq.planet.blog.event.listener;

import com.gjq.planet.blog.cache.redis.batch.UserSummerInfoCache;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.event.UserInfoModifyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author: gjq0117
 * @date: 2024/6/6 19:25
 * @description:
 */
@Component
public class UserInfoModifyListener {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSummerInfoCache userSummerInfoCache;

    /**
     *  修改用户聚合信息
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, fallbackExecution = true)
    public void modifyUserSummerCache(UserInfoModifyEvent event) {
        Long uid = event.getUid();
//        User user = userDao.getById(uid);
//        UserSummerInfoResp userSummerInfoResp = UserBuilder.buildUserSummerInfo(user);
        userSummerInfoCache.remove(uid);
    }
}
