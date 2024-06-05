package com.gjq.planet.blog.cache.redis.batch;

import cn.hutool.core.collection.CollectionUtil;
import com.gjq.planet.blog.dao.GroupMemberDao;
import com.gjq.planet.common.constant.IMRedisKey;
import com.gjq.planet.common.domain.entity.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/24 22:41
 * @description:
 */
@Component
public class GroupMemberCache extends AbstractRedisHashCache<GroupMember> {

    @Autowired
    private GroupMemberDao groupMemberDao;

    @Override
    protected String getRedisKey() {
        return "";
    }

    @Override
    protected String getRedisKey(Long keyNum) {
        return IMRedisKey.getKey(IMRedisKey.GROUP_MEMBER_INFO, keyNum);
    }

    @Override
    protected String getHashKey(GroupMember groupMember) {
        return String.valueOf(groupMember.getUid());
    }

    @Override
    protected List<GroupMember> load(List<Long> keys) {
        return CollectionUtil.isEmpty(keys) ? groupMemberDao.list() : groupMemberDao.listByIds(keys);
    }

    @Override
    protected List<GroupMember> load(Long keyNum, List<Long> keys) {
        return CollectionUtil.isEmpty(keys) ? groupMemberDao.listByGroupId(keyNum) : groupMemberDao.listByUidsAndGroup(keys, keyNum);
    }

    @Override
    protected Long getExpireSeconds() {
        return 60 * 60 * 24L;
    }
}
