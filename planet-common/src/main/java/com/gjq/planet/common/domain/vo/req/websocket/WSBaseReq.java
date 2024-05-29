package com.gjq.planet.common.domain.vo.req.websocket;

import lombok.Data;

/**
 * @author: gjq0117
 * @date: 2024/5/15 16:35
 * @description: ws请求的基础包
 */
@Data
public class WSBaseReq {

    /**
     * 请求类型
     *
     * @see com.gjq.planet.common.enums.im.WSReqTypeEnum
     */
    private Integer type;

    /**
     * 请求数据
     */
    private String data;
}
