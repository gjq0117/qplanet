package com.gjq.planet.common.domain.dto.msg;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: gjq0117
 * @date: 2024/5/29 22:10
 * @description: 撤回消息实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecallMsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("撤回消息ID")
    @NotNull(message = "撤回消息id不能为空")
    private Long msgId;

    @ApiModelProperty("撤回时间点")
    @NotNull(message = "撤回时间点不能为空")
    private Date recallTime;
}
