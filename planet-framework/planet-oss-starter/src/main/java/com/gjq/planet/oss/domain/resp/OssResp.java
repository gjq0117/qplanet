package com.gjq.planet.oss.domain.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/4/15 16:11
 * @description: 返回给前端的信息
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OssResp {
    /**
     * 上传的临时地址
     */
    private String uploadUrl;

    /**
     * 成功后能够下载的地址
     */
    String downloadUrl;
}
