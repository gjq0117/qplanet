package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.UserFriendMapper;
import com.gjq.planet.common.domain.entity.UserFriend;
import com.gjq.planet.common.domain.vo.req.userfriend.UserFriendPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.utils.CursorUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户好友表 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-05-18
 */
@Service
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {

    /**
     * 获取好友信息
     *
     * @param uid 用户id
     * @param fid 好友id
     * @return UserFriend
     */
    public UserFriend getByUidAndFId(Long uid, Long fid) {
        return lambdaQuery()
                .eq(UserFriend::getUid, uid)
                .eq(UserFriend::getFriendUid, fid)
                .one();
    }

    /**
     * 通过uid获取好友分页信息
     *
     * @param req req
     * @return CursorPageBaseResp<UserFriend>
     */
    public CursorPageBaseResp<UserFriend> pageFriendInfo(UserFriendPageReq req) {
        return CursorUtils.getCursorPageByMysql(this, req, wrapper -> {
            wrapper.eq(UserFriend::getUid, req.getCurrUid());
        }, UserFriend::getCreateTime);
    }

    /**
     * 获取好友详情
     *
     * @param currUid   当前用户ID
     * @param friendUid 好友ID
     * @return 好友信息
     */
    public UserFriend getFriendDetailed(Long currUid, Long friendUid) {
        return lambdaQuery()
                .eq(UserFriend::getUid, currUid)
                .eq(UserFriend::getFriendUid, friendUid)
                .one();
    }

    /**
     * 修改好友备注
     *
     * @param currUid  当前用户ID
     * @param friendId 好友ID
     * @param remark   备注
     */
    public Boolean putFriendRemark(Long currUid, Long friendId, String remark) {
        return lambdaUpdate()
                .set(UserFriend::getRemark, remark)
                .eq(UserFriend::getUid, currUid)
                .eq(UserFriend::getFriendUid, friendId)
                .update();
    }

    /**
     *  改变好友特别关心状态
     *
     * @param currUid 当前用户ID
     * @param friendUid 好友ID
     * @param type 是/否
     * @return Boolean
     */
    public Boolean changeFriendCare(Long currUid, Long friendUid, Integer type) {
        return lambdaUpdate()
                .set(UserFriend::getIsCare, type)
                .eq(UserFriend::getUid, currUid)
                .eq(UserFriend::getFriendUid, friendUid)
                .update();
    }
}
