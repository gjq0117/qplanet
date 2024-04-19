package com.gjq.planet.common.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/4/14 16:54
 * @description: ip详情
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpDetail implements Serializable {
    private String ip;
    private String isp;
    private String isp_id;
    private String city;
    private String city_id;
    private String country;
    private String country_id;
    private String region;
    private String region_id;
}
