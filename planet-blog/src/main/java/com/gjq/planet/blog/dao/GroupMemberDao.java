package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.GroupMemberMapper;
import com.gjq.planet.common.domain.entity.GroupMember;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 群成员信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Service
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {


    public GroupMember getByGroupIdAndUid(Long groupId, Long uid) {
        return lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getUid, uid)
                .one();
    }

    public List<GroupMember> listByGroupId(Long groupId) {
        return lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .list();
    }

    public List<GroupMember> listByUidsAndGroup(List<Long> uids, Long groupId) {
        return lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .in(GroupMember::getUid, uids)
                .list();
    }

    public List<GroupMember> ListByGroupId(Long groupId) {
        return lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .list();
    }

    public void removeByUidAndGroupId(Long uid, Long groupId) {
        this.remove(new QueryWrapper<GroupMember>().eq("uid", uid).eq("group_id", groupId));
    }
}
