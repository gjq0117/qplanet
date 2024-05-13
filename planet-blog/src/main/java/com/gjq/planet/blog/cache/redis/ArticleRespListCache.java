package com.gjq.planet.blog.cache.redis;

import com.gjq.planet.blog.dao.ArticleDao;
import com.gjq.planet.blog.service.IArticleService;
import com.gjq.planet.blog.utils.JsonUtils;
import com.gjq.planet.blog.utils.RedisUtils;
import com.gjq.planet.common.constant.RedisKey;
import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/4/24 12:14
 * @description: 文章列表缓存
 */
@Component
public class ArticleRespListCache {

    /**
     * key
     */
    private static final String key = RedisKey.getKey(RedisKey.ARTICLE_LIST);

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    @Lazy
    private IArticleService articleService;


    /**
     * 设置List缓存（hash）如果没有hash表就创建
     *
     * @param articleListRespList
     */
    public void setList(List<ArticleResp> articleListRespList) {
        for (ArticleResp resp :
                articleListRespList) {
            RedisUtils.hset(key, String.valueOf(resp.getId()), JsonUtils.toStr(resp), RedisKey.ARTICLE_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    /**
     * 设置一个缓存
     *
     * @param resp
     */
    public void setOne(ArticleResp resp) {
        RedisUtils.hset(key, String.valueOf(resp.getId()), JsonUtils.toStr(resp), RedisKey.ARTICLE_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
    }

    /**
     * 通过ID设置值
     * @param id
     */
    public void setById(Long id) {
        Article article = articleDao.getById(id);
        ArticleResp articleResp = articleService.transToResp(article);
        RedisUtils.hset(key, String.valueOf(id), JsonUtils.toStr(articleResp), RedisKey.ARTICLE_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
    }

    /**
     * 获取整个列表
     *
     * @return
     */
    public List<ArticleResp> getList() {
        Map<Object, Object> map = RedisUtils.hmget(key);
        return map.values().stream().map(obj -> JsonUtils.toObj((String) obj, ArticleResp.class)).collect(Collectors.toList());
    }

    /**
     * 获取缓存通过指定ID
     *
     * @return
     */
    public ArticleResp getById(Long id) {
        String json_str = (String) RedisUtils.hget(key, String.valueOf(id));
        if (StringUtils.isNotBlank(json_str)) {
            return JsonUtils.toObj(json_str, ArticleResp.class);
        }
        return null;
    }

    public List<ArticleResp> getListByIds(Long... ids) {
        List<ArticleResp> result = new ArrayList<>();
        for (Long id :
                ids) {
            String json_str = (String) RedisUtils.hget(key, String.valueOf(id));
            if (StringUtils.isNotBlank(json_str)) {
                result.add(JsonUtils.toObj(json_str, ArticleResp.class));
            }
        }
        return result;
    }

    /**
     * 批量从hash表中移除指定信息
     *
     * @param ids
     */
    public void removeByIds(Long... ids) {
        String[] idArr = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idArr[i] = String.valueOf(ids[i]);
        }
        RedisUtils.hdel(key, idArr);
    }

    /**
     * 移除hash表
     */
    public void removeALL() {
        RedisUtils.del(key);
    }
}
