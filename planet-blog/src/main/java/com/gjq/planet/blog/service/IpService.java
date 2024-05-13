package com.gjq.planet.blog.service;

import com.gjq.planet.common.domain.entity.IpDetail;

/**
 * @author: gjq0117
 * @date: 2024/4/15 11:00
 * @description: 处理ip业务
 */
public interface IpService {

    /**
     * 更新ip信息
     *
     * @param uid
     * @param ip
     */
    void refreshIpInfoAsync(Long uid, String ip);

    /**
     *  获取IP详情
     *
     * @param ip
     * @return
     */
    IpDetail getIpDetail(String ip);
}
