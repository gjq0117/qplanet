package com.gjq.planet.blog.controller;

import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.oss.domain.req.UploadUrlReq;
import com.gjq.planet.oss.domain.resp.OssResp;
import com.gjq.planet.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * @author: gjq0117
 * @date: 2024/4/16 15:13
 * @description: oss服务接口
 */
@RestController
@Api(tags = "oss服务接口")
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/getUploadPutUrl")
    @ApiOperation("获取临时且带签证的上传url(put方式)")
    public ApiResult<OssResp> getUploadUrl(@RequestBody @Valid UploadUrlReq uploadUrlReq) {
        return ApiResult.success(ossService.getUploadUrl(uploadUrlReq));
    }
}
