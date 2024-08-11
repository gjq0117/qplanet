package com.gjq.planet.common.domain.dto.msg;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/5/29 22:22
 * @description: 文件消息基类
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseFileMsgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件地址")
    @NotBlank(message = "文件地址不能为空")
    private String url;

    @ApiModelProperty("文件大小")
    private Long size;
}
