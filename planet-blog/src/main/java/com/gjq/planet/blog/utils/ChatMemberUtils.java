package com.gjq.planet.blog.utils;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gjq.planet.common.enums.blog.UserActiveStatusEnum;

/**
 * @author: gjq0117
 * @date: 2024/5/25 10:29
 * @description: 群成员工具类
 */
public class ChatMemberUtils {

    private static final String SEPARATOR = "_";

    public static Pair<UserActiveStatusEnum, String> getCursorPair(String cursor) {
        UserActiveStatusEnum activeStatusEnum = UserActiveStatusEnum.ONLINE;
        String timeCursor = null;
        if (StringUtils.isNotBlank(cursor)) {
            String activeStr = cursor.split(SEPARATOR)[0];
            String timeStr = cursor.split(SEPARATOR)[1];
            activeStatusEnum = UserActiveStatusEnum.of(Integer.valueOf(activeStr));
            timeCursor = timeStr;
        }
        return Pair.of(activeStatusEnum, timeCursor);
    }

    public static String generateCursor(UserActiveStatusEnum activeStatusEnum, String timeCursor) {
        return activeStatusEnum.getStatus() + SEPARATOR + timeCursor;
    }
}
