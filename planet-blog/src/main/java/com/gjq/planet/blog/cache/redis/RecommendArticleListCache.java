package com.gjq.planet.blog.cache.redis;

import com.gjq.planet.blog.dao.ArticleDao;
import com.gjq.planet.blog.service.adapter.ArticleBuilder;
import com.gjq.planet.blog.utils.JsonUtils;
import com.gjq.planet.blog.utils.RedisUtils;
import com.gjq.planet.common.constant.RedisKey;
import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.domain.vo.resp.article.RecommendArticleResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/1 16:32
 * @description: 推荐文章列表缓冲器
 */
@Component
public class RecommendArticleListCache {

    /**
     * key
     */
    private static final String key = RedisKey.getKey(RedisKey.RECOMMEND_LIST);

    @Autowired
    private ArticleDao articleDao;

    /**
     * 设置List缓存（hash）如果没有hash表就创建
     *
     * @param recommendArticleRespList
     */
    public void setList(List<RecommendArticleResp> recommendArticleRespList) {
        for (RecommendArticleResp resp :
                recommendArticleRespList) {
            RedisUtils.hset(key, String.valueOf(resp.getId()), JsonUtils.toStr(resp), RedisKey.RECOMMEND_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    /**
     * 设置一个缓存
     *
     * @param resp
     */
    public void setOne(RecommendArticleResp resp) {
        RedisUtils.hset(key, String.valueOf(resp.getId()), JsonUtils.toStr(resp), RedisKey.RECOMMEND_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
    }

    /**
     * 通过ID设置缓存
     *
     * @param articleId
     */
    public void setById(Long articleId) {
        Article article = articleDao.getById(articleId);
        RecommendArticleResp resp = ArticleBuilder.buildRecommendArticleResp(article);
        RedisUtils.hset(key, String.valueOf(articleId), JsonUtils.toStr(resp), RedisKey.RECOMMEND_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
    }

    /**
     * 获取整个列表
     *
     * @return
     */
    public List<RecommendArticleResp> getList() {
        Map<Object, Object> map = RedisUtils.hmget(key);
        return map.values().stream().map(obj -> JsonUtils.toObj((String) obj, RecommendArticleResp.class)).collect(Collectors.toList());
    }

    /**
     * 通过ID删除指定vlaue
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
