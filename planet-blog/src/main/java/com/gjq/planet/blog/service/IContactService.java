package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.contact.ContactPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.contact.ContactResp;

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
}
