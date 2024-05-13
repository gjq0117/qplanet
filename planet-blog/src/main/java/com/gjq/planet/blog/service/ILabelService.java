package com.gjq.planet.blog.service;


import com.gjq.planet.common.domain.vo.req.label.LabelReq;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;

import java.util.List;

/**
 * <p>
 * 标签信息 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
public interface ILabelService {

    /**
     *  新增一条标签
     *
     * @param req
     */
    void insertOne(LabelReq req);

    /**
     *  获取标签信息列表
     *
     * @return
     */
    List<LabelResp> list();

    /**
     *  通过id更新label信息
     *
     * @param req
     */
    void updateById(LabelReq req);

    /**
     *  通过id删除标签信息
     *
     * @param labelId
     */
    void deleteById(Long labelId);

    /**
     *  通过key模糊查询标签列表
     *
     * @param searchKey
     * @return
     */
    List<LabelResp> searchByKey(String searchKey);

    /**
     *  通过id获取label信息
     *
     * @param labelId
     * @return
     */
    LabelResp getById(Long labelId);

    /**
     *  通过分类id获取该分类下的标签数
     *
     * @param id
     * @return
     */
    Integer getLabelNumBySortId(Long id);

    /**
     *  通过分类id获取标签列表
     *
     * @param sortId
     * @return
     */
    List<LabelResp> getListBySortId(Long sortId);
}
