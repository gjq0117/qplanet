package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.springCache.WebInfoCache;
import com.gjq.planet.blog.dao.ArticleDao;
import com.gjq.planet.blog.dao.WebInfoDao;
import com.gjq.planet.blog.service.ISortService;
import com.gjq.planet.blog.service.IWebInfoService;
import com.gjq.planet.blog.service.adapter.WebInfoBuilder;
import com.gjq.planet.blog.utils.AssertUtil;
import com.gjq.planet.common.domain.entity.WebInfo;
import com.gjq.planet.common.domain.vo.req.webinfo.WebInfoUpdateReq;
import com.gjq.planet.common.domain.vo.resp.sort.SortResp;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebInfoResp;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebStatisticsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * @author: gjq0117
 * @date: 2024/4/18 17:24
 * @description: webInfo业务处理
 */
@Service
public class WebInfoServiceImpl implements IWebInfoService {

    @Autowired
    private WebInfoCache webInfoCache;

    @Autowired
    private WebInfoDao webInfoDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ISortService sortService;

    @Override
    public WebInfoResp getWebInfo() {
        WebInfo webInfo = webInfoCache.getWebInfo();
        return WebInfoBuilder.buildResp(webInfo);
    }

    @Override
    public void updateOrSave(WebInfoUpdateReq req) {
        AssertUtil.isNotEmpty(req, "更新webInfo时，客户端传入过来的WebInfoUpdateReq为空");
        WebInfo webInfo = WebInfoBuilder.buildFromReq(req);
        if (Objects.isNull(webInfo.getId())) {
            // 不存在就保存
            webInfoDao.save(webInfo);
            return;
        }
        webInfoDao.updateById(webInfo);
        // 清空缓存
        webInfoCache.evictWebInfo();
    }

    @Override
    public WebStatisticsInfo getWebStatisticsInfo() {
        List<SortResp> list = sortService.list();
        int sortCount = 0;
        if (Objects.nonNull(list)) {
            sortCount = Math.toIntExact(list.stream().filter(sortResp ->
                    sortResp.getArticleNum() > 0
            ).count());
        }
        return WebStatisticsInfo.builder()
                .articleCount(articleDao.getPublishCount())
                .sortCount(sortCount)
                .visitCount(Optional.ofNullable(webInfoDao.getFirstOne()).map(WebInfo::getViewCount).orElse(0L))
                .build();
    }

    @Override
    public void addVisitCount() {
        // 更新网站访问量
        WebInfo webInfo = webInfoDao.getFirstOne();
        if (Objects.isNull(webInfo)) {
            return;
        }
        webInfo.setViewCount(Optional.of(webInfo).map(WebInfo::getViewCount).orElse(0L) + 1);
        webInfoDao.updateById(webInfo);
    }

}
