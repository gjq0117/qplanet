package com.gjq.planet.common.constant;

/**
 * @author: gjq0117
 * @date: 2024/4/28 18:37
 * @description: 邮箱常量
 */
public class MailConstant {

    /**
     * 一天最多可发送的验证码邮件
     */
    private static final int DAY_MAX_EMAIL_TIMES = 3;

    /**
     * 邮箱主题
     */
    public static final String MAIL_SUBJECT = "PLANET给你发送了一条消息";

    /**
     * 文本标题（注册验证码）
     */
    public static final String MAIL_TITLE_REGISTER_CODE = "您收到来自%s发送的注册验证码";

    /**
     * 文本标题（修改密码验证码）
     */
    public static final String MAIL_TITLE_MODIFY_PWD_CODE = "您收到来自%s发送的修改密码的验证码";

    /**
     * 文本内容模板（注册验证码）
     */
    public static final String MAIL_TEXT_REGISTER_CODE = "【%s】%s为本次注册的验证码，请在%d分钟内完成验证操作。为保证账号安全，切勿泄露此验证码";

    /**
     * 文本内容模板（修改密码验证码）
     */
    public static final String MAIL_TEXT_MODIFY_PWD_CODE = "【%s】%s为本次修改密码的验证码，请在%d分钟内完成验证操作。为保证账号安全，切勿泄露此验证码";


    /**
     * 邮箱HTML模板
     */
    private static final String MAIL_TEMPLATE = "<div style=\"font-family: serif;line-height: 22px;padding: 30px\">\n" +
            "    <div style=\"display: flex;flex-direction: column;align-items: center\">\n" +
            "        <div style=\"margin: 10px auto 20px;text-align: center\">\n" +
            "            <div style=\"line-height: 32px;font-size: 26px;font-weight: bold;color: #000000\">\n" +
            "                嘿！你在 %s 中收到一条新消息。\n" +
            "            </div>\n" +
            "            <div style=\"font-size: 16px;font-weight: bold;color: rgba(0, 0, 0, 0.19);margin-top: 21px\">\n" +
            "                %s\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div style=\"min-width: 250px;max-width: 800px;min-height: 128px;background: #F7F7F7;border-radius: 10px;padding: 32px\">\n" +
            "            <div>\n" +
            "                <div style=\"font-size: 18px;font-weight: bold;color: #C5343E\">\n" +
            "                    %s\n" +
            "                </div>\n" +
            "                <div style=\"margin-top: 6px;font-size: 16px;color: #000000\">\n" +
            "                    <p>\n" +
            "                        %s\n" +
            "                    </p>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            %s\n" +
            "            <a style=\"width: 150px;height: 38px;background: #ef859d38;border-radius: 32px;display: flex;align-items: center;justify-content: center;text-decoration: none;margin: 40px auto 0\"\n" +
            "               href=\"https://qplanet.cn\" target=\"_blank\">\n" +
            "                <span style=\"color: #DB214B\">有朋自远方来</span>\n" +
            "            </a>\n" +
            "        </div>\n" +
            "        <div style=\"margin-top: 20px;font-size: 12px;color: #00000045\">\n" +
            "            此邮件由 %s 自动发出，直接回复无效（一天最多发送 " + DAY_MAX_EMAIL_TIMES + " 条验证码邮件），退订请联系站长。\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</div>";

    /**
     * 获取邮箱内容
     *
     * @param webName     网站名称
     * @param title       标题
     * @param receiveName 接收人姓名
     * @param content     文本内容
     * @param epilogue    结语
     * @param sendName    发件人姓名
     * @return
     */
    public static String getMailText(String webName, String title, String receiveName, String content, String epilogue, String sendName) {
        return String.format(MAIL_TEMPLATE, webName, title, receiveName, content, epilogue, sendName);
    }

    /**
     * 获取邮箱注册的文本内容
     *
     * @return
     */
    public static String getRegCodeMailText(String code, String receiveName) {
        return String.format(MAIL_TEMPLATE, CommonConstant.WEB_NAME, String.format(MAIL_TITLE_REGISTER_CODE, CommonConstant.WEB_NAME), receiveName, String.format(MAIL_TEXT_REGISTER_CODE, CommonConstant.WEB_NAME, code, RedisKey.REGISTER_CODE_EXPIRE_MINUTES), "", CommonConstant.WEB_NAME);
    }

    public static String getModifyPwdCodeMailText(String code, String receiveName) {
        return String.format(MAIL_TEMPLATE, CommonConstant.WEB_NAME, String.format(MAIL_TITLE_MODIFY_PWD_CODE, CommonConstant.WEB_NAME), receiveName, String.format(MAIL_TEXT_MODIFY_PWD_CODE, CommonConstant.WEB_NAME, code, RedisKey.MODIFY_PWD_EXPIRE_MINUTES), "", CommonConstant.WEB_NAME);
    }


}
