package com.gjq.planet.blog.service;

import com.gjq.planet.common.enums.common.ItemTypeEnum;

/**
 * <p>
 * 物品表 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-06-28
 */
public interface IItemConfigService {

    /**
     *  添加新的物品
     *
     * @param imgUrl 物品图片
     * @param itemTypeEnum 物品类型枚举
     * @return 物品ID
     */
    Long addNewItem(String imgUrl, ItemTypeEnum itemTypeEnum);
}
