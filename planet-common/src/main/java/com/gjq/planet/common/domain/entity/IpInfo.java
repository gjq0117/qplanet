package com.gjq.planet.common.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: gjq0117
 * @date: 2024/4/14 16:53
 * @description: ip信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IpInfo implements Serializable {

    /**
     * 注册时的ip
     */
    private String createIp;

    /**
     * 注册时的ip详情
     */
    private IpDetail createIpDetail;

    /**
     * 最新登录的ip
     */
    private String updateIp;

    /**
     * 最新登录的ip
     */
    private IpDetail updateIpDetail;

    /**
     *  是否需要刷新ip(判断传入过来的ip是否与updateIp一致，不一致时需要刷新)
     *
     * @param ip
     */
    public boolean needRefreshIpDetail(String ip) {
        return !Objects.equals(ip, updateIp);
    }
}
