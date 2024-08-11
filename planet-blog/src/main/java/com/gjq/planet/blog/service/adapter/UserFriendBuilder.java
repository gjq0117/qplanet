package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.User;
import com.gjq.planet.common.domain.entity.UserFriend;
import com.gjq.planet.common.domain.vo.resp.user.UserSummerInfoResp;
import com.gjq.planet.common.domain.vo.resp.userfriend.FriendDetailedResp;
import com.gjq.planet.common.enums.common.YesOrNoEnum;
import org.springframework.beans.BeanUtils;

/**
 * @author: gjq0117
 * @date: 2024/5/27 14:36
 * @description: 用户好友建造者
 */
public class UserFriendBuilder {


    public static UserFriend buildUserFriend(Long currUid, Long targetId) {
        return UserFriend.builder()
                .uid(currUid)
                .friendUid(targetId)
                // 默认不是特别关心
                .isCare(YesOrNoEnum.NO.getCode())
                .build();
    }

    public static FriendDetailedResp buildFriendDetailed(UserFriend userFriend, User friend) {
        FriendDetailedResp resp = new FriendDetailedResp();
        resp.setGender(friend.getGender());
        resp.setIsActive(friend.getIsActive());
        resp.setRemark(userFriend.getRemark());
        resp.setIsCare(userFriend.getIsCare());

        UserSummerInfoResp userSummerInfoResp = UserBuilder.buildUserSummerInfo(friend);
        BeanUtils.copyProperties(userSummerInfoResp, resp);
        return resp;
    }
}
