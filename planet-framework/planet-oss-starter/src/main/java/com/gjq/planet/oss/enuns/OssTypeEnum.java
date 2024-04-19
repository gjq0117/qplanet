package com.gjq.planet.oss.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gjq0117
 * @date: 2024/4/15 16:13
 * @description: 提供除了Minio适配其他厂商的支持。
 */
@Getter
@AllArgsConstructor
public enum OssTypeEnum {
    /**
     * Minio 对象存储
     */
    MINIO("minio", 1),

    /**
     * 华为 OBS
     */
    OBS("obs", 2),

    /**
     * 腾讯 COS
     */
    COS("tencent", 3),

    /**
     * 阿里巴巴 SSO
     */
    ALIBABA("alibaba", 4),
    ;

    /**
     * 名称
     */
    final String name;
    /**
     * 类型
     */
    final int type;
}
