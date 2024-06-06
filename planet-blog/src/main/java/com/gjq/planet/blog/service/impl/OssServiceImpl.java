package com.gjq.planet.blog.service.impl;

import com.gjq.planet.common.utils.AssertUtil;
import com.gjq.planet.common.utils.RequestHolder;
import com.gjq.planet.common.domain.dto.RequestInfo;
import com.gjq.planet.oss.domain.req.OssReq;
import com.gjq.planet.oss.domain.req.UploadUrlReq;
import com.gjq.planet.oss.domain.resp.OssResp;
import com.gjq.planet.oss.enuns.OssSceneEnum;
import com.gjq.planet.oss.service.OssService;
import com.gjq.planet.oss.utils.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: gjq0117
 * @date: 2024/4/16 14:50
 * @description:
 */
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private MinioTemplate minioTemplate;

    @Override
    public OssResp getUploadUrl(UploadUrlReq req) {
        RequestInfo requestInfo = RequestHolder.get();
        AssertUtil.isNotEmpty(requestInfo, "请先登录!!!");
        Long uid = RequestHolder.get().getUid();
        OssSceneEnum sceneEnum = OssSceneEnum.of(req.getScene());
        AssertUtil.isNotEmpty(sceneEnum, "场景错误!!!");
        OssReq ossReq = OssReq.builder()
                .fileName(req.getFileName())
                .filePath(sceneEnum.getPath())
                .uid(uid)
                // 开启自动生成地址，防止地址重复
                .autoPath(true)
                .build();
        return minioTemplate.getPreSignedObjectUrl(ossReq);
    }

}
