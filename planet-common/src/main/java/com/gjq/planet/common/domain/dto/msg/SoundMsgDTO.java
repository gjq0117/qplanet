package com.gjq.planet.common.domain.dto.msg;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: gjq0117
 * @date: 2024/5/29 22:38
 * @description: 语音消息实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SoundMsgDTO extends BaseFileMsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("时长（秒）")
    @NotNull(message = "时长不能为空")
    private Integer second;
}
