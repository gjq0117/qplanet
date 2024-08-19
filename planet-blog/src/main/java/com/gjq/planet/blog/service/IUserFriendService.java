package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.userfriend.PutFriendRemarkReq;
import com.gjq.planet.common.domain.vo.req.userfriend.UserFriendPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.groupmember.GroupMemberResp;
import com.gjq.planet.common.domain.vo.resp.userfriend.FriendDetailedResp;
import com.gjq.planet.common.enums.im.RoomTypeEnum;

/**
 * <p>
 * 用户好友表 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-05-18
 */
public interface IUserFriendService {

    /**
     * 判断uid对应的用户是否是currUid对应的用户的好友
     *
     * @param currUid 当前用户
     * @param uid     指定用户
     * @return 是否是好友
     */
    Boolean isFriend(Long currUid, Long uid);

    /**
     * 获取好友列表分页信息
     *
     * @param req 分页请求
     * @return 好友列表
     */
    CursorPageBaseResp<GroupMemberResp> pageFriendInfo(UserFriendPageReq req);

    /**
     * 获取特别关心好友分页信息
     *
     * @param req 分页请求
     * @return 特别关心好友列表
     */
    CursorPageBaseResp<GroupMemberResp> pageCareFriendInfo(UserFriendPageReq req);

    /**
     * 获取好友详情信息
     *
     * @param currUid   当前用户ID
     * @param friendUid 好友ID
     * @return 好友信息详情
     */
    FriendDetailedResp getFriendDetailed(Long currUid, Long friendUid);

    /**
     * 设置好友备注
     *
     * @param currUid 当前用户ID
     * @param req     req
     */
    void putFriendRemark(Long currUid, PutFriendRemarkReq req);

    /**
     * 设置好友为特别关心
     *
     * @param currUid   当前用户ID
     * @param friendUid 好友ID
     */
    void putFriendToCare(Long currUid, Long friendUid);

    /**
     *  取消好友的特别关心
     *
     * @param currUid 当前用户ID
     * @param friendUid 好友ID
     */
    void cancelFriendCare(Long currUid, Long friendUid);

    /**
     *  删除指定好友
     *
     * @param currUid 当前用户ID
     * @param friendUid 好友ID
     */
    void deleteFriend(Long currUid, Long friendUid);

    /**
     *  初始化聊天环境
     *
     * @param uid1 uid1
     * @param uid2 uid2
     * @param roomTypeEnum 房间类型（一般为单聊或者机器人房间）
     * @return 房间号
     */
    Long initSingleChatEnvironment(Long uid1, Long uid2, RoomTypeEnum roomTypeEnum);
}
