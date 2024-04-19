package com.gjq.planet.oss.service;

import com.gjq.planet.oss.domain.req.UploadUrlReq;
import com.gjq.planet.oss.domain.resp.OssResp;

/**
 * @author: gjq0117
 * @date: 2024/4/16 14:40
 * @description: oss业务
 */
public interface OssService {

    /**
     * 获取文件上传路径
     *
     * @param req
     * @return
     */
    OssResp getUploadUrl(UploadUrlReq req);
}
