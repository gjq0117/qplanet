package com.gjq.planet.blog.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjq.planet.blog.mapper.LabelMapper;
import com.gjq.planet.common.domain.entity.Label;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签信息 服务实现类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
@Service
public class LabelDao extends ServiceImpl<LabelMapper, Label> {

    public Integer getNumBySortId(Long sortId) {
        return lambdaQuery()
                .eq(Label::getSortId, sortId)
                .count().intValue();
    }

    public List<Label> getListBySortId(Long sortId) {
        return lambdaQuery()
                .eq(Label::getSortId, sortId)
                .list();
    }

    /**
     * 删除指定SortId下所有的标签
     *
     * @param sortId
     */
    public Boolean removeBySortId(Long sortId) {
        return this.remove(new QueryWrapper<Label>().eq("sort_id", sortId));
    }
}
