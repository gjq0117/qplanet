package com.gjq.planet.blog.dao;

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

    /**
     *  通过群组ID过去用户列表
     *
     * @param groupId groupId
     */
    public List<Long> getMemberPageByGroupId(Long groupId) {

        return null;
    }
}
