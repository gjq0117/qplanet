package com.gjq.planet.oss.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @Description: 上传url请求入参
 * @Author: gjq
 * @Date: 2023-03-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("上传url请求入参")
public class UploadUrlReq {

    @ApiModelProperty(value = "文件名（带后缀）")
    @NotBlank
    private String fileName;

    @ApiModelProperty(value = "上传场景1.博客,2.社区")
    @NotNull
    private Integer scene;
}
