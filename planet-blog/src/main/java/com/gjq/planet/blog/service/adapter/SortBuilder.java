package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.Sort;
import com.gjq.planet.common.domain.vo.req.sort.SortReq;
import com.gjq.planet.common.domain.vo.resp.article.ArticleResp;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortPageResp;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/4/19 22:36
 * @description: 分类信息实体构建
 */
public class SortBuilder {

    /**
     * 通过请求实体构建分类信息
     *
     * @param req
     * @return
     */
    public static Sort buildFormReq(SortReq req) {
        Sort sort = new Sort();
        BeanUtils.copyProperties(req, sort);
        return sort;
    }

    /**
     *  构建分类界面所需要的信息
     *
     * @param articleRespList 分类界面所需要的文章信息
     * @param sortResp 分类信息
     * @param labelRespList 标签信息列表
     */
    public static SortPageResp buildSortPageResp(List<ArticleResp> articleRespList, SortResp sortResp, List<LabelResp> labelRespList) {
        return SortPageResp.builder()
                .articleRespList(articleRespList)
                .sortResp(sortResp)
                .labelResp(labelRespList)
                .build();
    }
}
