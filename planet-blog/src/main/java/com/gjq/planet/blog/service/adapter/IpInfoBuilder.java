package com.gjq.planet.blog.service.adapter;

import com.gjq.planet.common.domain.entity.IpDetail;
import com.gjq.planet.common.domain.entity.IpInfo;

/**
 * @author: gjq0117
 * @date: 2024/4/15 11:57
 * @description: ip信息是培训
 */
public class IpInfoBuilder {

    public static IpInfo buildIpInfo(IpDetail ipDetail) {
        String ip = ipDetail.getIp();
        return IpInfo.builder()
                .createIp(ip)
                .updateIp(ip)
                .createIpDetail(ipDetail)
                .updateIpDetail(ipDetail)
                .build();
    }
}
