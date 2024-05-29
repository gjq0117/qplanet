package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/5/25 10:49
 * @description:
 */
public class GroupMemberBuilder {

    /**
     * 通过用户列表构建群成员列表
     *
     * @param userList 用户列表
     * @return 群成员列表
     */
    public static List<GroupMemberResp> buildGroupMember(List<User> userList) {
        List<GroupMemberResp> result = new ArrayList<>();
        if (Objects.nonNull(userList) && !userList.isEmpty()) {
            for (User user : userList) {
                GroupMemberResp groupMemberResp = GroupMemberResp.builder()
                        .uid(user.getId())
                        .isActive(user.getIsActive()).build();
                result.add(groupMemberResp);
            }
        }
        return result;
    }
}
