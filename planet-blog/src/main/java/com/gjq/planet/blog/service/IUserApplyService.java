package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.userapply.UserApplyPageReq;
import com.gjq.planet.common.domain.vo.req.userapply.FriendApplyReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.userapply.FriendApplyResp;

/**
 * <p>
 * 用户申请表 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-05-18
 */
public interface IUserApplyService {

    /**
     *  好友申请
     *
     * @param req 请求
     */
    void friendApply(FriendApplyReq req);

    /**
     *  通过当前用户id获取好友申请列表游标分页
     *
     * @param req 分页请求
     * @return List<FriendApplyResp>
     */
    CursorPageBaseResp<FriendApplyResp> pageFriendApplyList(UserApplyPageReq req);

    /**
     *  同意好友申请
     *
     * @param currUid 当前用户id
     * @param targetId 目标用户id
     */
    void agreeFriendApply(Long currUid, Long targetId);

    /**
     *  拒绝好友申请
     *
     * @param currUid 当前用户Id
     * @param targetId 目标用户Id
     */
    void rejectFriendApply(Long currUid, Long targetId);
}
