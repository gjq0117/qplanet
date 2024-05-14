package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.webinfo.WebInfoUpdateReq;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebInfoResp;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebStatisticsInfo;

/**
 * <p>
 * 网站信息 服务类
 * </p>
 *
 * @author gjq
 * @since 2024-04-13
 */
public interface IWebInfoService {

    /**
     *  获取网站基本信息
     *
     */
    WebInfoResp getWebInfo();

    /**
     *  通过id更新网站信息
     *
     */
    void updateOrSave(WebInfoUpdateReq req);

    /**
     *  获取网站统计数据
     *
     */
    WebStatisticsInfo getWebStatisticsInfo();

    /**
     *  网站访问量+1
     */
    void addVisitCount();

}
