package com.gjq.planet.blog.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.gjq.planet.common.domain.entity.UserApply;
import com.gjq.planet.common.domain.vo.req.userapply.FriendApplyReq;
import com.gjq.planet.common.domain.vo.resp.userapply.FriendApplyResp;
import com.gjq.planet.common.enums.im.ApplyStatusEnum;
import com.gjq.planet.common.enums.im.ReadStatusEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/26 15:23
 * @description: 用户申请构建器
 */
public class UserApplyBuilder {

    /**
     * 构建好友申请实体
     *
     * @param req
     * @return
     */
    public static UserApply buildFriendApply(FriendApplyReq req) {
        UserApply userApply = new UserApply();
        BeanUtil.copyProperties(req, userApply);
        userApply.setStatus(ApplyStatusEnum.PENDING_REVIEW.getStatus());
        userApply.setReadStatus(ReadStatusEnum.UNREAD.getStatus());
        return userApply;
    }

    public static List<FriendApplyResp> buildFriendApplyResp(List<UserApply> userApplyList) {
        return userApplyList.stream().map(userApply -> {
            FriendApplyResp resp = new FriendApplyResp();
            BeanUtil.copyProperties(userApply, resp);
            return resp;
        }).collect(Collectors.toList());
    }
}
