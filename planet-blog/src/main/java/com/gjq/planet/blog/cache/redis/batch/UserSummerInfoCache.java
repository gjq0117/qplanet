package com.gjq.planet.blog.cache.redis.batch;

import cn.hutool.core.collection.CollectionUtil;
import com.gjq.planet.blog.dao.UserDao;
import com.gjq.planet.blog.service.adapter.UserBuilder;
import com.gjq.planet.common.constant.IMRedisKey;
import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.resp.user.UserSummerInfoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/23 11:37
 * @description: 用户聚合信息缓存
 */
@Component
public class UserSummerInfoCache extends AbstractRedisHashCache<UserSummerInfoResp> {

    @Autowired
    private UserDao userDao;

    @Override
    protected String getRedisKey() {
        return IMRedisKey.getKey(IMRedisKey.USER_SUMMER_INFO);
    }

    @Override
    protected String getRedisKey(Long keyNum) {
        return "";
    }

    @Override
    protected String getHashKey(UserSummerInfoResp userSummerInfoResp) {
        return String.valueOf(userSummerInfoResp.getUid());
    }

    @Override
    protected List<UserSummerInfoResp> load(List<Long> keys) {
        List<User> userList = CollectionUtil.isEmpty(keys) ? userDao.list() : userDao.listByIds(keys);
        if (CollectionUtil.isEmpty(userList)) {
            return null;
        }
        return userList.stream()
                .map(UserBuilder::buildUserSummerInfo)
                .collect(Collectors.toList());
    }

    @Override
    protected Long getExpireSeconds() {
        return 60 * 5L;
    }
}
