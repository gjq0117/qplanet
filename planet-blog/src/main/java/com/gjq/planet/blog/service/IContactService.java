package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.contact.ContactPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.contact.ContactResp;

import java.util.Set;

/**
 * <p>
 * 会话信息表 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
public interface IContactService {

    /**
     * 根据用户ID获取会话列表分页信息
     *
     * @param uid 用户id
     * @param req 分页请求
     * @return 分页信息
     */
    CursorPageBaseResp<ContactResp> getContactListPage(Long uid, ContactPageReq req);

    /**
     *  创建好友会话
     *
     * @param roomId
     * @param uid1
     * @param uid2
     */
    void createFriendContact(Long roomId, Long uid1, Long uid2);

    /**
     *  向指定用户推送新的会话
     *
     * @param roomId 房间好
     * @param uidSet 用户ID列表
     * @return
     */
    void pushNewContact(Long roomId, Set<Long> uidSet);

    /**
     *  主动拉取会话响应信息
     *
     * @param uid 用户ID
     * @param roomId 房间号
     * @return
     */
    ContactResp getContactResp(Long uid, Long roomId);
}
