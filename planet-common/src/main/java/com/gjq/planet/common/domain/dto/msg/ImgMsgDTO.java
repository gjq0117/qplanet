package com.gjq.planet.common.domain.dto.msg;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/5/29 22:27
 * @description: 图片消息实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ImgMsgDTO extends BaseFileMsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("图片高度")
    @NotNull(message = "图片高度不能为空")
    private Long height;

    @ApiModelProperty("图片宽度")
    @NotNull(message = "图片宽度不能为空")
    private Long width;
}
