package com.gjq.planet.oss.config;


import com.gjq.planet.oss.enuns.OssTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: gjq0117
 * @date: 2024/4/15 16:14
 * @description: oss配置信息
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    /**
     * 是否开启
     */
    Boolean enabled;

    /**
     * 存储对象服务器类型
     */
    OssTypeEnum type;

    /**
     * OSS 访问端点，集群时需提供统一入口
     */
    String endpoint;

    /**
     * 用户名
     */
    String accessKey;

    /**
     * 密码
     */
    String secretKey;

    /**
     * 默认存储桶名，没有指定时，会放在默认的存储桶
     */
    String bucketName;
}
