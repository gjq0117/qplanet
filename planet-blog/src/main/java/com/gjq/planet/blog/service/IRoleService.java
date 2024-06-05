package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.entity.GroupMember;

/**
 * @author: gjq0117
 * @date: 2024/5/30 15:40
 * @description: 角色接口
 */
public interface IRoleService {

    /**
     *  判断这个群成员是否有AT全体成员的权限
     *
     * @param groupMember groupMember
     */
    Boolean chatHasAtAll(GroupMember groupMember);
}
