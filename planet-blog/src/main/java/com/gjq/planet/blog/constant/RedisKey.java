package com.gjq.planet.blog.constant;

/**
 * @author: gjq0117
 * @date: 2024/4/15 12:20
 * @description: 本系统存放redis中的key
 */
public class RedisKey {

    /**
     *  系统根目录
     */
    private static final String BASE_KEY = "planet:blog:";

    /**
     *  用户token的key
     */
    public static final String USER_TOKEN_STRING = "userToken:uid_%d";

    public static String getKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
