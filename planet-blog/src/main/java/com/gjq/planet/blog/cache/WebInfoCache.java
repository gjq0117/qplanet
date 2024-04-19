package com.gjq.planet.blog.cache;

import com.gjq.planet.blog.dao.WebInfoDao;
import com.gjq.planet.common.domain.entity.WebInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author: gjq0117
 * @date: 2024/4/18 17:44
 * @description: 网站基础信息缓存
 */
@Component
public class WebInfoCache {

    @Autowired
    private WebInfoDao webInfoDao;

    /**
     * 设置缓存
     *
     * @return
     */
    @Cacheable(cacheNames = "webInfo")
    public WebInfo getWebInfo() {
        return webInfoDao.getFirstOne();
    }

    /**
     * 清空缓存
     */
    @CacheEvict(cacheNames = "webInfo",allEntries = true)
    public void evictWebInfo() {
    }
}
