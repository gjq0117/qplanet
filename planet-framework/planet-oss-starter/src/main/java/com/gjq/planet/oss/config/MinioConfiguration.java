package com.gjq.planet.oss.config;


import com.gjq.planet.oss.utils.MinioTemplate;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: gjq0117
 * @date: 2024/4/15 16:20
 * @description:
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MinioClient.class})
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnExpression("${oss.enabled}")
@ConditionalOnProperty(value = "oss.type", havingValue = "minio")
public class MinioConfiguration {
    @Bean
    @SneakyThrows
    @ConditionalOnMissingBean(MinioClient.class)
    public MinioClient minioClient(OssProperties ossProperties) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(ossProperties.getEndpoint())
                .credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
                .build();
        return minioClient;
    }

    @Bean
    @ConditionalOnBean({MinioClient.class})
    @ConditionalOnMissingBean(MinioTemplate.class)
    public MinioTemplate minioTemplate(MinioClient minioClient, OssProperties ossProperties) {
        return new MinioTemplate(minioClient, ossProperties);
    }
}
