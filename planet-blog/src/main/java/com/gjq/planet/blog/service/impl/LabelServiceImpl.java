package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.springCache.LabelCache;
import com.gjq.planet.blog.cache.springCache.SortCache;
import com.gjq.planet.blog.dao.LabelDao;
import com.gjq.planet.blog.service.ILabelService;
import com.gjq.planet.blog.service.ISortService;
import com.gjq.planet.blog.service.adapter.LabelBuilder;
import com.gjq.planet.common.domain.entity.Label;
import com.gjq.planet.common.domain.vo.req.label.LabelReq;
import com.gjq.planet.common.domain.vo.resp.label.LabelResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author: gjq0117
 * @date: 2024/4/21 11:51
 * @description: 标签业务
 */
@Service
public class LabelServiceImpl implements ILabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private LabelCache labelCache;

    @Autowired
    private ISortService sortService;

    @Autowired
    private SortCache sortCache;

    @Override
    public void insertOne(LabelReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        Label label = LabelBuilder.buildFromReq(req);
        if (Objects.nonNull(label)) {
            labelDao.save(label);
            labelCache.evictList();
            sortCache.evictSortList();
        }
    }

    @Override
    public List<LabelResp> list() {
        return labelCache.getList();
    }

    @Override
    public void updateById(LabelReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        Label label = LabelBuilder.buildFromReq(req);
        boolean success = labelDao.updateById(label);
        if (success) {
            // 清空缓存
            labelCache.evictList();
        }
    }

    @Override
    public void deleteById(Long labelId) {
        if (Objects.isNull(labelId)) {
            return;
        }
        boolean success = labelDao.removeById(labelId);
        if (success) {
            labelCache.evictList();
        }
    }

    @Override
    public List<LabelResp> searchByKey(String searchKey) {
        return labelCache.getList()
                .stream()
                .filter(labelResp -> labelResp.getLabelName().contains(searchKey) || labelResp.getLabelDescription().contains(searchKey))
                .collect(Collectors.toList());
    }

    @Override
    public LabelResp getById(Long labelId) {
        if (Objects.isNull(labelId)) {
            return null;
        }
        Label label = labelDao.getById(labelId);
        LabelResp labelResp = new LabelResp();
        BeanUtils.copyProperties(label, labelResp);
        // 分装分类信息
        labelResp.setSortResp(sortService.getRespById(label.getSortId()));
        return labelResp;
    }

    @Override
    public Integer getLabelNumBySortId(Long sortId) {
        if (Objects.isNull(sortId)) {
            return null;
        }
        return labelDao.getNumBySortId(sortId);
    }

    @Override
    public List<LabelResp> getListBySortId(Long sortId) {
        if (Objects.isNull(sortId)) {
            return null;
        }
        List<Label> labelList = labelDao.getListBySortId(sortId);
        List<LabelResp> result = null;
        if (Objects.nonNull(labelList) && labelList.size() > 0) {
            result = labelList.stream().map(label -> {
                LabelResp labelResp = new LabelResp();
                BeanUtils.copyProperties(label, labelResp);
                return labelResp;
            }).collect(Collectors.toList());
        }
        return result;
    }

}
