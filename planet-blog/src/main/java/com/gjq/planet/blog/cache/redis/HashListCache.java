package com.gjq.planet.blog.cache.redis;

import com.gjq.planet.common.utils.JsonUtils;
import com.gjq.planet.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/18 11:04
 * @description: 通用缓存工具（list->hash） 将list映射成hash  T => 指的是需要存储对象的类型  K => 指的是指定键的类型
 */
@Slf4j
public class HashListCache<T, K> {

    /**
     * key
     */
    private final String key;

    /**
     * 过期时间
     */
    private final Integer expireTime;

    /**
     * 时间单位
     */
    private final TimeUnit timeUnit;

    /**
     * 想要作为hashKey的属性（必须是类型“T”的属性之一）
     */
    private final String attributeForHashKey;

    /**
     *  T的class
     */
    private final Class<T> type;

    public HashListCache(String key, Integer expireTime, TimeUnit timeUnit, String attributeForHashKey) {
        this.key = key;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
        this.attributeForHashKey = attributeForHashKey;
        // 获取T的Class
        Type superclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        this.type = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    /**
     * 将list通过hash的形式存入redis
     *
     * @param list 目标值
     */
    public void setList(List<T> list) {
        for (T t : list) {
            RedisUtils.hset(key, getHashKey(t, attributeForHashKey), JsonUtils.toStr(t), expireTime, timeUnit);
        }
    }

    public List<T> getList() {
        Map<Object, Object> map = RedisUtils.hmget(key);
        return map.values().stream().map(obj -> JsonUtils.toObj((String) obj,type)).collect(Collectors.toList());
    }

    /**
     * 将指定对象以hash的形式存入缓存，并以指定的attributeForHashKey为key
     *
     * @param t 目标值
     */
    public void setOne(T t) {
        RedisUtils.hset(key, getHashKey(t, attributeForHashKey), JsonUtils.toStr(t));
    }

    /**
     *  通过key获取对应的值
     *
     * @param k k
     */
    public T getByKey(K k) {
        String json_str = (String) RedisUtils.hget(key, k.toString());
        if (StringUtils.isNotBlank(json_str)) {
            return JsonUtils.toObj(json_str, type);
        }
        return null;
    }

    /**
     * 获取key
     *
     * @param t       t
     * @param hashKey hashKey
     * @return key
     */
    private String getHashKey(T t, String hashKey) {
        Class<?> aClass = t.getClass();
        try {
            Field field = aClass.getDeclaredField(hashKey);
            // 设置可见
            field.setAccessible(true);
            return String.valueOf(field.get(t));
        } catch (Exception e) {
            log.error("属性解析失败：{}中不存在属性：{},errMsg: {}", aClass.getName(), hashKey, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
