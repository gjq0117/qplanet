package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.entity.ItemConfig;
import com.gjq.planet.common.domain.vo.req.userbackpack.ImgEmojiPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;

/**
 * <p>
 * 用户背包表 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-06-28
 */
public interface IUserBackpackService {

    /**
     * 指定用户添加表情包
     *
     * @param uid    用户ID
     * @param imgUrl 表情包地址
     */
    void addImgEmoji(Long uid, String imgUrl);

    /**
     * 给指定用户发放物品
     *
     * @param uid      用户ID
     * @param itemId   物品ID
     * @param quantity 数量
     */
    void acquireItem(Long uid, Long itemId, Integer quantity);

    /**
     *  游标分页获取表情包
     *
     * @param req
     * @param uid 用户ID （传null就查全部）
     * @return
     */
    CursorPageBaseResp<ItemConfig> getImgEmojiPage(ImgEmojiPageReq req, Long uid);
}
