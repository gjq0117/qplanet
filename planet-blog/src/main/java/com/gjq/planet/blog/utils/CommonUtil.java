package com.gjq.planet.blog.utils;

import java.util.Calendar;
import java.util.Random;

/**
 * @author: gjq0117
 * @date: 2024/4/28 18:01
 * @description: 通用攻击类
 */
public class CommonUtil {

    /**
     * 创建随机指定位数字码
     *
     * @param length 验证码长度
     * @return
     */
    public static String getRandomCode(Integer length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    /**
     *  获取现在到第二天零点的秒数
     *
     * @return
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    public static void main(String[] args) {
        System.out.println(getSecondsNextEarlyMorning());
    }
}
