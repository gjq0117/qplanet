package com.gjq.planet.blog.config.redis;

import com.gjq.planet.blog.cache.redis.SortListCache;
import com.gjq.planet.blog.cache.springCache.SortCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/5/15 08:55
 * @description: redis过期回调监听器
 */
@Component
public class RedisMessageListener implements MessageListener {

    @Autowired
    private SortCache sortCache;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = message.toString();
        if (Objects.nonNull(expireKey) && expireKey.equals(SortListCache.key)) {
            // 定位到分类文章,如果redis中缓存的分类文章过期，那么就删除本地的分类缓存
            sortCache.evictSortList();
        }
    }
}
