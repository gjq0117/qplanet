package com.gjq.planet.common.domain.dto;

import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/4/14 14:52
 * @description: 请求信息
 */
@Data
public class RequestInfo {

    /**
     *  用户id
     */
    private Long uid;

    /**
     *  请求id
     */
    private String ip;
}
