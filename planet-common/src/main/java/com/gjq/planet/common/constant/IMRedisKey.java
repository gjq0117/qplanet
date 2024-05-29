package com.gjq.planet.common.constant;

/**
 * @author: gjq0117
 * @date: 2024/5/18 10:50
 * @description: IM系统存放缓存的Key
 */
public class IMRedisKey {

    /**
     * 系统根目录
     */
    private static final String BASE_KEY = "planet:im:";

    /**
     * 用户聚合信息
     */
    public static final String USER_SUMMER_INFO = "userSummerInfo";

    /**
     * 房间信息
     */
    public static final String ROOM_INFO = "roomInfo";

    /**
     * 房间-群聊信息
     */
    public static final String ROOM_GROUP_INFO = "roomGroupInfo";

    /**
     *  房间成员信息
     */
    public static final String GROUP_MEMBER_INFO = "groupMemberInfo:groupId_%d";

    /**
     * 用户信息列表
     */
    public static final String userInfoList = "userInfoList:";

    /**
     * 用户列表过期时间（天）
     */
    public static final Integer userInfoListExpireDays = 3;

    /**
     * 获取key
     *
     * @param key key
     * @param o   参数列表
     * @return key
     */
    public static String getKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
