package com.gjq.planet.common.utils;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gjq.planet.common.enums.blog.UserActiveStatusEnum;

import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/5/25 10:29
 * @description: 游标分割工具类
 */
public class CursorSplitUtils {

    private static final String SEPARATOR = "_";

    /**
     *  适配在线状态+时间戳的游标
     *
     * @param cursor
     * @return
     */
    public static Pair<UserActiveStatusEnum, String> getActiveStatusPair(String cursor) {
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

    public static String generateActiveStatusCursor(UserActiveStatusEnum activeStatusEnum, String timeCursor) {
        return activeStatusEnum.getStatus() + SEPARATOR + timeCursor;
    }

    /**
     *  适配boolean + 时间戳类型的游标
     *
     * @param cursor
     * @return
     */
    public static Pair<Boolean, String> getBooleanPair(String cursor) {
        Boolean bool = Boolean.FALSE;
        String timeCursor = "";
        if (StringUtils.isNotBlank(cursor)) {
            String boolStr = Optional.ofNullable(cursor.split(SEPARATOR)[0]).orElse("false");
            bool = Boolean.parseBoolean(boolStr);
            timeCursor = Optional.ofNullable(cursor.split(SEPARATOR)[1]).orElse("");
        }
        return Pair.of(bool, timeCursor);
    }

    public static String generateBoolCursor(Boolean bool, String timeCursor) {
        return bool + SEPARATOR + timeCursor;
    }
}
