package com.gjq.planet.blog.service;


import com.gjq.planet.common.domain.vo.req.sort.SortReq;
import com.gjq.planet.common.domain.vo.resp.sort.HasArticleSortResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortPageResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;

import java.util.List;

/**
 * <p>
 * 分类信息 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
public interface ISortService {

    /**
     *  插入一个分类信息
     *
     * @param req
     */
    void insertOne(SortReq req);

    /**
     *  获取分类信息列表
     *
     * @return
     */
    List<SortResp> list();

    /**
     *  通过id删除分类信息
     *
     * @param sortId
     */
    void deleteById(Long sortId);

    /**
     *  通过id更新sort信息
     *
     * @param req
     */
    void updateById(SortReq req);

    /**
     *  通过key模糊查询分类列表
     *
     * @param searchKey
     * @return
     */
    List<SortResp> searchByKey(String searchKey);

    /**
     *  通过id获取分类响应实体
     *
     * @param sortId
     * @return
     */
    SortResp getRespById(Long sortId);

    /**
     *  获取有文章的分类信息以及该分类下最新的六篇文章
     *
     * @return
     */
    List<HasArticleSortResp> getSortAndNewArticleForSix();

    /**
     *  获取分类及其下面的所有文章信息
     *
     * @return
     */
    List<HasArticleSortResp> getSortAndArticle();

    /**
     *  通过sortId以及labelId查询对应的文章列表
     *
     * @param sortId 分类ID
     * @param labelId 标签ID
     * @return
     */
    SortPageResp getSortPageResp(Long sortId, Long labelId);
}
