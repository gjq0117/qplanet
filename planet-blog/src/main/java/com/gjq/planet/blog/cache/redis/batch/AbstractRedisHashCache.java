package com.gjq.planet.blog.cache.redis.batch;

import com.gjq.planet.common.utils.JsonUtils;
import com.gjq.planet.common.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/23 10:49
 * @description: redis hash类型批量缓存框架
 */
public abstract class AbstractRedisHashCache<VALUE> implements BatchCache<Long, VALUE> {

    /**
     * VALUE的class
     */
    private final Class<VALUE> type;

    public AbstractRedisHashCache() {
        // 获取T的Class
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.type = (Class<VALUE>) genericSuperclass.getActualTypeArguments()[0];
    }

    /**
     * 获取redis Key
     *
     * @return key
     */
    protected abstract String getRedisKey();

    /**
     * 获取多元Key指(需要拼接的key)
     *
     * @param keyNum keyNum
     */
    protected abstract String getRedisKey(Long keyNum);

    /**
     * 获取hashKey
     *
     * @return String
     */
    protected abstract String getHashKey(VALUE value);

    /**
     * 从数据库中加载
     *
     * @param keys keys
     */
    protected abstract List<VALUE> load(List<Long> keys);

    /**
     * 从数据库中加载
     */
    protected abstract List<VALUE> load(Long keyNum, List<Long> keys);

    /**
     * 获取过期秒数
     *
     * @return s
     */
    protected abstract Long getExpireSeconds();

    @Override
    public VALUE get(Long key) {
        String json_str = (String) RedisUtils.hget(getRedisKey(), String.valueOf(key));
        return getVALUE(json_str, key, null);
    }

    @Override
    public VALUE get(Long keyNum, Long key) {
        String json_str = (String) RedisUtils.hget(getRedisKey(keyNum), String.valueOf(key));
        return getVALUE(json_str, key, keyNum);
    }

    @Override
    public void put(VALUE value) {
        RedisUtils.hset(getRedisKey(), getHashKey(value), JsonUtils.toStr(value));
    }

    @Override
    public void put(Long keyNum, VALUE value) {
        RedisUtils.hset(getRedisKey(keyNum), getHashKey(value), JsonUtils.toStr(value));
    }

    @Override
    public void setBatch(List<VALUE> values) {
        for (VALUE value : values) {
            RedisUtils.hset(getRedisKey(), getHashKey(value), JsonUtils.toStr(value));
        }
    }

    @Override
    public void setBatch(Long keyNum, List<VALUE> values) {
        for (VALUE value : values) {
            RedisUtils.hset(getRedisKey(keyNum), getHashKey(value), JsonUtils.toStr(value));
        }
    }

    @Override
    public List<VALUE> getBatch(List<Long> keys) {
        // 获取全量数据
        Map<Object, Object> map = RedisUtils.hmget(getRedisKey());
        return getOrLoadListFromMap(map, keys, null);
    }

    @Override
    public List<VALUE> getBatch(Long keyNum, List<Long> keys) {
        // 获取全量数据
        Map<Object, Object> map = RedisUtils.hmget(getRedisKey(keyNum));
        return getOrLoadListFromMap(map, keys, keyNum);
    }

    @Override
    public void removeBatch(List<Long> integers) {
        RedisUtils.hdel(getRedisKey(), integers.stream().map(Objects::toString).distinct().toArray());
    }

    @Override
    public void removeBatch(Long keyNum, List<Long> integers) {
        RedisUtils.hdel(getRedisKey(keyNum), integers.stream().map(Objects::toString).distinct().toArray());
    }

    @Override
    public void remove(Long key) {
        RedisUtils.hdel(getRedisKey(), String.valueOf(key));
    }

    @Override
    public void remove(Long keyNum, Long key) {
        RedisUtils.hdel(getRedisKey(keyNum), String.valueOf(key));
    }

    private VALUE getVALUE(String jsonStr, Long key, Long keyNum) {
        if (StringUtils.isNotBlank(jsonStr)) {
            return JsonUtils.toObj(jsonStr, type);
        }
        // DB加载
        List<VALUE> load = load(Collections.singletonList(key));
        VALUE value = load.get(0);
        if (Objects.nonNull(keyNum)) {
            put(keyNum, value);
        } else {
            put(value);
        }
        return value;
    }

    private List<VALUE> getOrLoadListFromMap(Map<Object, Object> map, List<Long> keys, Long keyNum) {
        Set<Long> keySet = map.keySet().stream().map(o ->
                Long.parseLong(o.toString())
        ).collect(Collectors.toSet());
        if (Objects.isNull(keys) || keys.isEmpty()) {
            // 获取所有值
            List<VALUE> collect = map.values().stream().map(value -> JsonUtils.toObj((String) value, type)).collect(Collectors.toList());
            if (collect.isEmpty()) {
                List<VALUE> loadValues;
                if (Objects.nonNull(keyNum)) {
                    loadValues = load(keyNum, keys);
                } else {
                    loadValues = load(keys);
                }
                // 加载到缓存
                loadInRedis(loadValues, keyNum);
                return loadValues;
            }
            return collect;
        }
        // 计算差集 没有就去数据库load
        List<VALUE> result = new ArrayList<>();
        List<Long> loadList = keys.stream().filter(key -> !keySet.contains(key)).collect(Collectors.toList());
        if (!loadList.isEmpty()) {
            List<VALUE> loadValues = load(loadList);
            // 加载到缓存
            if (!CollectionUtils.isEmpty(loadValues)) {
                loadInRedis(loadValues, keyNum);
                result.addAll(loadValues);
            }
        }
        // 从redis全量数据中过滤需要的数据
        for (Long key : keys) {
            if (keySet.contains(key)) {
                result.add((JsonUtils.toObj((String) map.get(String.valueOf(key)), type)));
            }
        }
        return result;
    }

    private void loadInRedis(List<VALUE> loadValues, Long keyNum) {
        if (CollectionUtils.isEmpty(loadValues)) return;
        if (Objects.nonNull(keyNum)) {
            setBatch(keyNum, loadValues);
        } else {
            setBatch(loadValues);
        }
    }
}
