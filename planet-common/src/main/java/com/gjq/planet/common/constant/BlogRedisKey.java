package com.gjq.planet.common.constant;

/**
 * @author: gjq0117
 * @date: 2024/4/15 12:20
 * @description: 博客系统存放redis中的key
 */
public class BlogRedisKey {

    /**
     * 系统根目录
     */
    private static final String BASE_KEY = "planet:blog:";

    /**
     * ========================== 用户token的key ==========================
     */
    public static final String USER_TOKEN_STRING = "userToken:uid_%d";

    /**
     * token过期日期（天）
     */
    public static final int TOKEN_EXPIRE_DAYS = 1;


    /**
     * ========================== 文章列表 ==========================
     */
    public static final String ARTICLE_LIST = "articleList:";

    /**
     * 文章列表进过期时间（天）
     */
    public static final int ARTICLE_LIST_EXPIRE_DAYS = 1;


    /**
     * ========================== 注册验证码 ==========================
     */
    public static final String REGISTER_CODE = "registerCode:username_%S";

    /**
     * 注册验证码过期时间（分钟）
     */
    public static final int REGISTER_CODE_EXPIRE_MINUTES = 5;

    /**
     * ===================== 修改密码验证码 ==========================
     */
    public static final String MODIFY_PWD_CODE = "modifyPwdCode:email_%s";

    /**
     * 修改密码验证码过期事件（分分钟）
     */
    public static final int MODIFY_PWD_EXPIRE_MINUTES = 5;

    /**
     * =================== 用户列表 ==============================
     */
    public static final String USER_LIST = "userList:";

    /**
     * 用户列表过期时间
     */
    public static final int USER_LIST_EXPIRE_DAYS = 3;

    /**
     * =================== 访客记录(IP) ==============================
     */
    public static final String VISITOR_IP_INFO = "visitorInfo:ip_%s";

    /**
     *  =================== 访客记录(UID) ==============================
     */
    public static final String VISITOR_UID_INFO = "visitorInfo:uid_%d";

    /**
     * ================== 推荐文章列表 ============================
     */
    public static final String RECOMMEND_LIST = "recommendList:";

    /**
     * 推荐文章列表过期时间（天）
     */
    public static final int RECOMMEND_LIST_EXPIRE_DAYS = 1;

    /**
     *  ================== 分类及其分类下面所有的文章的缓存 =================
     */
    public static final String SORT_ARTICLE_LIST = "sortArticleList:";

    /**
     *  分类及其分类下面所有的文章的缓存的过期时间（天）
     */
    public static final int SORT_ARTICLE_LIST_EXPIRE_DAYS = 1;

    /**
     * 获取key
     *
     * @param key
     * @param o
     * @return
     */
    public static String getKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
