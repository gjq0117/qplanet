package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.visitor.VisitorListReq;
import com.gjq.planet.common.domain.vo.resp.visitor.VisitorListResp;
import com.gjq.planet.common.domain.vo.resp.visitor.VisitorProvinceResp;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 网站访问信息 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-04-30
 */
public interface IVisitorService {

    /**
     *  访问网站
     */
    void visitWeb();

    /**
     *  保存用户登录后的网站访问信息
     *
     * @param uid 用户ID
     * @param ip IP (不能为空)
     */
    void saveWebVisitInfo(Long uid, @NotNull String ip);

    /**
     *  保存文章访问信息
     *
     * @param articleId
     */
    void saveArticleVisitInfo(Long articleId);

    /**
     *  获取访问的省份信息
     *
     * @return
     */
    List<VisitorProvinceResp> getProvinceList();

    /**
     *  获取访问信息列表
     *
     * @param req
     * @return
     */
    List<VisitorListResp> getVisitorList(VisitorListReq req);

}
