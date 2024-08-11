package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.dao.ItemConfigDao;
import com.gjq.planet.blog.service.IItemConfigService;
import com.gjq.planet.common.domain.entity.ItemConfig;
import com.gjq.planet.common.enums.common.ItemTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: gjq0117
 * @date: 2024/6/28 22:30
 * @description:
 */
@Service
public class ItemConfigServiceImpl implements IItemConfigService {

    @Autowired
    private ItemConfigDao itemConfigDao;

    @Override
    public Long addNewItem(String imgUrl, ItemTypeEnum itemTypeEnum) {
        ItemConfig itemConfig = ItemConfig.builder()
                .img(imgUrl)
                .type(ItemTypeEnum.IMG_EMOJI.getType())
                .description(ItemTypeEnum.IMG_EMOJI.getDesc())
                .build();
        itemConfigDao.save(itemConfig);
        return itemConfig.getId();
    }
}
