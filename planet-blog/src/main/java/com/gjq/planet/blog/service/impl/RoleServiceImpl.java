package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.service.IRoleService;
import com.gjq.planet.common.domain.entity.GroupMember;
import com.gjq.planet.common.enums.im.ChatGroupRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/5/30 15:40
 * @description:
 */
@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {

    @Override
    public Boolean chatHasAtAll(GroupMember groupMember) {
        if (Objects.nonNull(groupMember)) {
            ChatGroupRoleEnum chatGroupRoleEnum = ChatGroupRoleEnum.of(groupMember.getRole());
            // 群主或者群管理
            return chatGroupRoleEnum.equals(ChatGroupRoleEnum.GROUP_MANAGE) || chatGroupRoleEnum.equals(ChatGroupRoleEnum.GROUP_LEADER);
        }
        return false;
    }
}
