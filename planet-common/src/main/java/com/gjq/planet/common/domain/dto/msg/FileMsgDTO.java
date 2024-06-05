package com.gjq.planet.common.domain.dto.msg;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/5/29 22:29
 * @description: 文件消息实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FileMsgDTO extends BaseFileMsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件名")
    private String fileName;
}
