package com.gjq.planet.blog.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.MessageMapper;
import com.gjq.planet.common.domain.entity.Message;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessagePageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.utils.CursorUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

    /**
     * 计算两条消息之间的间隔
     */
    public Integer getGapCount(Long roomId, Long fromId, Long toId) {
        return lambdaQuery()
                .eq(Message::getRoomId, roomId)
                .gt(Message::getId, fromId)
                .le(Message::getId, toId)
                .count();
    }

    /**
     * 消息列表游标分页
     *
     * @param roomId
     * @param req
     * @return
     */
    public CursorPageBaseResp<Message> getCursorPage(Long roomId, ChatMessagePageReq req) {
        return CursorUtils.getCursorPageByMysql(this, req, wrapper -> {
            wrapper.eq(Message::getRoomId, roomId);
        }, Message::getId);
    }
}
