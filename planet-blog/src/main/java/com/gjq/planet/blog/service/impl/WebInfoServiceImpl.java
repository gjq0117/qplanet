package com.gjq.planet.blog.service.impl;

import com.gjq.planet.blog.cache.WebInfoCache;
import com.gjq.planet.blog.dao.WebInfoDao;
import com.gjq.planet.blog.service.IWebInfoService;
import com.gjq.planet.blog.service.adapter.WebInfoBuilder;
import com.gjq.planet.blog.utils.AssertUtil;
import com.gjq.planet.common.domain.entity.WebInfo;
import com.gjq.planet.common.domain.vo.req.webinfo.WebInfoUpdateReq;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebInfoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


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

    @Override
    public WebInfoResp getWebInfo() {
        WebInfo webInfo = webInfoCache.getWebInfo();
        WebInfoResp result = WebInfoBuilder.buildResp(webInfo);
        return result;
    }

    @Override
    public void updateById(WebInfoUpdateReq req) {
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

}
