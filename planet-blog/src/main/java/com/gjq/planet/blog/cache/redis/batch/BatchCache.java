package com.gjq.planet.blog.cache.redis.batch;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/23 10:40
 * @description: 批量缓存框架
 */
public interface BatchCache<KEY, VALUE> {

    /**
     * 获取单个
     *
     * @param key k
     * @return v
     */
    VALUE get(KEY key);

    /**
     * 获取指定hash中的key
     *
     * @param keyNum keyNum
     * @param key    key
     * @return v
     */
    VALUE get(Long keyNum, KEY key);

    /**
     * 设置/更新单个
     *
     * @param value v
     */
    void put(VALUE value);

    /**
     * 在指定hash列表中设置/更新单个
     *
     * @param keyNum keyNum
     * @param value  v
     */
    void put(Long keyNum, VALUE value);

    /**
     * 批量获取列表
     *
     * @param keys keys
     * @return values
     */
    List<VALUE> getBatch(List<KEY> keys);

    /**
     * 在指定hash列表中批量获取列表
     *
     * @param keyNum keyNum
     * @param keys   keys
     * @return values
     */
    List<VALUE> getBatch(Long keyNum, List<KEY> keys);


    /**
     * 批量设置列表
     *
     * @param values values
     */
    void setBatch(List<VALUE> values);

    /**
     * 在指定hash列表中批量设置列表
     *
     * @param keyNum keyNum
     * @param values values
     */
    void setBatch(Long keyNum, List<VALUE> values);

    /**
     * 批量删除
     *
     * @param keys keys
     */
    void removeBatch(List<KEY> keys);

    /**
     * 在指定hash表中批量删除
     *
     * @param keyNum keyNum
     * @param keys keys
     */
    void removeBatch(Long keyNum ,List<KEY> keys);

    /**
     * 单个删除
     *
     * @param key key
     */
    void remove(KEY key);

    /**
     * 在指定hash表中单个删除
     *
     * @param keyNum keyNum
     * @param key key
     */
    void remove(Long keyNum, KEY key);
}
