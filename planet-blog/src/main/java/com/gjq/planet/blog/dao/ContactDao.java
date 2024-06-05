package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.ContactMapper;
import com.gjq.planet.common.domain.entity.Contact;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.domain.vo.req.contact.ContactPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.utils.CursorUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会话信息表 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Service
public class ContactDao extends ServiceImpl<ContactMapper, Contact> {

    /**
     * 游标分页 根据uid获取对应的会话列表
     */
    public CursorPageBaseResp<Contact> getCursorPage(Long uid, ContactPageReq req) {
        return CursorUtils.getCursorPageByMysql(this, req, wrapper -> {
            wrapper.eq(Contact::getUid, uid);
        }, Contact::getActiveTime);
    }

    /**
     * 批量刷新会话的活跃时间
     *
     * @param roomId  房间ID
     * @param message 消息
     * @param uidList 用户idList
     */
    public Boolean refreshActiveTime(Long roomId, Message message, List<Long> uidList) {
        return lambdaUpdate()
                .set(Contact::getActiveTime, message.getCreateTime())
                .set(Contact::getLastMsgId, message.getId())
                .in(Contact::getUid, uidList)
                .eq(Contact::getRoomId, roomId)
                .update();
    }
}
