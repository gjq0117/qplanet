package com.gjq.planet.blog.cache.redis;

import com.gjq.planet.blog.dao.ArticleDao;
import com.gjq.planet.blog.dao.SortDao;
import com.gjq.planet.blog.service.IArticleService;
import com.gjq.planet.common.utils.JsonUtils;
import com.gjq.planet.common.utils.RedisUtils;
import com.gjq.planet.common.constant.BlogRedisKey;
import com.gjq.planet.common.domain.entity.Article;
import com.gjq.planet.common.domain.entity.Sort;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.common.domain.vo.resp.sort.HasArticleSortResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: gjq0117
 * @date: 2024/5/1 16:51
 * @description: 分类列表及其分类下所有文章的缓存配置
 */
@Component
public class SortListCache {

    /**
     * key
     */
    public static final String key = BlogRedisKey.getKey(BlogRedisKey.SORT_ARTICLE_LIST);
    /**
     *  过期时间
     */
    public static final int SORT_ARTICLE_LIST_EXPIRE_DAYS = BlogRedisKey.SORT_ARTICLE_LIST_EXPIRE_DAYS;

    @Autowired
    private SortDao sortDao;

    @Autowired
    @Lazy
    private IArticleService articleService;

    @Autowired
    @Lazy
    private ArticleDao articleDao;

    /**
     * 设置List缓存（hash）如果没有hash表就创建
     *
     * @param articleSortRespList 文章列表
     */
    public void setList(List<HasArticleSortResp> articleSortRespList) {
        // 倒序存
        for (int i = 0; i < articleSortRespList.size(); i++) {
            HasArticleSortResp resp = articleSortRespList.get(articleSortRespList.size() - i - 1);
            RedisUtils.hset(key, String.valueOf(resp.getId()), JsonUtils.toStr(resp), SORT_ARTICLE_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    /**
     * 通过ID设置缓存
     *
     * @param sortId 分类ID
     */
    public void setById(Long sortId) {
        HasArticleSortResp resp = new HasArticleSortResp();
        Sort sort = sortDao.getById(sortId);
        BeanUtils.copyProperties(sort, resp);
        List<ArticleResp> articleRespList = articleService.getArticleList().stream()
                .filter(articleListResp -> articleService.isView(articleListResp) && sortId.equals(articleListResp.getSortResp().getId()))
                .sorted(Comparator.comparing(ArticleResp::getPublishTime).reversed())
                .collect(Collectors.toList());
        resp.setArticleRespList(articleRespList);
        RedisUtils.hset(key, String.valueOf(sortId), JsonUtils.toStr(resp), SORT_ARTICLE_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
    }

    /**
     * 设置一个值
     *
     * @param resp
     */
    public void setOne(HasArticleSortResp resp) {
        RedisUtils.hset(key, String.valueOf(resp.getId()), JsonUtils.toStr(resp), SORT_ARTICLE_LIST_EXPIRE_DAYS, TimeUnit.DAYS);
    }

    public HasArticleSortResp getById(Long sortId) {
        String json_str = (String) RedisUtils.hget(key, String.valueOf(sortId));
        if (StringUtils.isNotBlank(json_str)) {
            return JsonUtils.toObj(json_str, HasArticleSortResp.class);
        }
        return null;
    }


    /**
     * 获取整个列表
     *
     * @return
     */
    public List<HasArticleSortResp> getList() {
        Map<Object, Object> map = RedisUtils.hmget(key);
        return map.values().stream().map(obj -> JsonUtils.toObj((String) obj, HasArticleSortResp.class)).collect(Collectors.toList());
    }

    /**
     * 下架文章
     *
     * @param articleId
     */
    public void removeShelves(Long articleId) {
        Article article = articleDao.getById(articleId);
        HasArticleSortResp resp = this.getById(article.getSortId());
        if (Objects.nonNull(resp)) {
            List<ArticleResp> articleRespList = resp.getArticleRespList();
            for (ArticleResp articleResp :
                    articleRespList) {
                if (articleResp.getId().equals(articleId)) {
                    articleRespList.remove(articleResp);
                    break;
                }
            }
            // 重新设置
            if (articleRespList.isEmpty()) {
                // 为空就直接删除整个分类信息
                removeByIds(article.getSortId());
            } else {
                resp.setArticleRespList(articleRespList);
                setOne(resp);
            }
        }
    }

    /**
     * 通过ID删除指定 Value
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
