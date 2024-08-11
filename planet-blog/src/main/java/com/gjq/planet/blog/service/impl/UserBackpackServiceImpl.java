package com.gjq.planet.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.gjq.planet.blog.dao.UserBackpackDao;
import com.gjq.planet.blog.service.IItemConfigService;
import com.gjq.planet.blog.service.IUserBackpackService;
import com.gjq.planet.common.domain.entity.ItemConfig;
import com.gjq.planet.common.domain.entity.UserBackpack;
import com.gjq.planet.common.domain.vo.req.userbackpack.ImgEmojiPageReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.enums.common.ItemStatusEnum;
import com.gjq.planet.common.enums.common.ItemTypeEnum;
import com.gjq.planet.lock.annotation.RedissonLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/6/28 17:29
 * @description:
 */
@Service
public class UserBackpackServiceImpl implements IUserBackpackService {

    @Autowired
    private IItemConfigService iItemConfigService;

    @Autowired
    private UserBackpackDao userBackpackDao;

    @Autowired
    @Lazy
    private IUserBackpackService userBackpackService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addImgEmoji(Long uid, String imgUrl) {
        //1、添加一个表情包的物品
        Long itemId = iItemConfigService.addNewItem(imgUrl, ItemTypeEnum.IMG_EMOJI);
        //2、给当前用户发放这个物品
        userBackpackService.acquireItem(uid, itemId, 1);
    }

    // 分布式锁
    @RedissonLock(key = "#uid", waitTime = 5000)
    @Override
    public void acquireItem(Long uid, Long itemId, Integer quantity) {
        UserBackpack userBackpack = userBackpackDao.getItemByUidAndItemId(uid, itemId);
        if (Objects.nonNull(userBackpack)) {
            // 用户背包中存在这个物品
            userBackpack.setQuantity(Optional.ofNullable(userBackpack.getQuantity()).orElse(0) + quantity);
            userBackpackDao.updateById(userBackpack);
        } else {
            // 向背包中添加物品并设置初始数量
            UserBackpack insert = UserBackpack.builder()
                    .uid(uid)
                    .itemId(itemId)
                    .quantity(quantity)
                    .status(ItemStatusEnum.EFFECTIVE.getStatus())
                    .build();
            userBackpackDao.save(insert);
        }
    }

    @Override
    public CursorPageBaseResp<ItemConfig> getImgEmojiPage(ImgEmojiPageReq req, Long uid) {
        List<ItemConfig> imgEmojiList = userBackpackDao.pageItemConfig(req, uid, ItemTypeEnum.IMG_EMOJI.getType(), ItemStatusEnum.EFFECTIVE.getStatus());
        String cursor =  Optional.ofNullable(CollectionUtil.getLast(imgEmojiList))
                .map(ItemConfig::getCreateTime)
                .map(createTime -> String.valueOf(createTime.getTime()))
                .orElse(null);
        Boolean isLast = imgEmojiList.size() != req.getPageSize();
        return new CursorPageBaseResp<>(cursor, isLast, imgEmojiList);
    }
}
