package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.groupmember.GroupMemberReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;

/**
 * <p>
 * 群成员信息 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
public interface IGroupMemberService {

    /**
     * 获取群成员分页信息
     */
    CursorPageBaseResp<GroupMemberResp> getGroupMemberPage(GroupMemberReq req);

    /**
     *  加入系统大群聊
     *
     * @param uid
     */
    void joinSystemGroup(Long uid);

    /**
     *  指定用户离开系统群聊
     *
     * @param uid
     */
    void leaveSystemGroup(Long uid);

    /**
     *  获取群组用户at分页信息
     *
     * @param atPageReq
     * @return
     */
    CursorPageBaseResp<Long> getGroupMemberAtPage(GroupMemberReq atPageReq);
}
