package com.gjq.planet.common.valid;

/**
 * @author: gjq0117
 * @date: 2024/4/30 11:33
 * @description: 正则表达式常量枚举
 */
public class RegexpConstant {

    /**
     * 系统用户名正则表达式
     */
    public static final String USERNAME_REGEXP = "[a-zA-Z]{8,15}";

    /**
     * 系统用户名正则表达式提示消息
     */
    public static final String USERNAME_MESSAGE = "用户名只能是8-15位以内英文字母";

    // ============================================================================================

    /**
     * 系统密码正则表达式
     */
    public static final String PASSWORD_REGEXP = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

    /**
     * 系统密码正则表达式提示消息
     */
    public static final String PASSWORD_MESSAGE = "密码长度介于8-16位之前，且必须为数字+字母的组合";


    // =============================================================================================

    /**
     * 系统手机号正则表达式
     */
    public static final String PHONE_REGEXP = "^1[3456789]\\d{9}$";

    /**
     * 系统手机号正则表达式消息提示
     */
    public static final String PHONE_MESSAGE = "手机号格式不对，请输入正确的手机号";

    // =============================================================================================

    /**
     * 系统昵称正则表达式
     */
    public static final String NICKNAME_REGEXP = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]{2,8}$";

    /**
     * 系统昵称正则表达式消息提示
     */
    public static final String NICKNAME_MASSAGE = "昵称只能包含字母、数字、下划线或中文字符,不能以下划线开头或结尾，且在2-8位之间";

    /**
     * 系统好友备注正则表达式
     */
    public static final String FRIEND_REMARK_REGEXP = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]{0,10}$";

    /**
     * 系统好友备注表达式消息提示
     */
    public static final String FRIEND_REMARK_MASSAGE = "昵称只能包含字母、数字、下划线或中文字符,不能以下划线开头或结尾，且最多十个字符";
}
