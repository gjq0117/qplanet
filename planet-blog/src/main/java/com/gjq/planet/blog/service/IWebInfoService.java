package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.vo.req.webinfo.WebInfoUpdateReq;
import com.gjq.planet.common.domain.vo.resp.webinfo.WebInfoResp;

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
     * @return
     */
    WebInfoResp getWebInfo();

    /**
     *  通过id更新网站信息
     *
     * @param req
     */
    void updateById(WebInfoUpdateReq req);
}
