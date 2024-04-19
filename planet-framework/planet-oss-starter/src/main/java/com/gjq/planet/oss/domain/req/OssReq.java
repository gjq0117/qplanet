package com.gjq.planet.oss.domain.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/4/16 12:00
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssReq {

    /**
     * 文件存储路径
     */
    private String filePath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 请求的uid
     */
    private Long uid;
    /**
     * 自动生成地址
     */
    @Builder.Default
    private boolean autoPath = true;
}
