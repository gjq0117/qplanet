package com.gjq.planet.common.domain.vo.resp.websocket.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/15 18:28
 * @description: 人数列表（在线/不在线）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("人数列表（在线/不在线）")
public class ChatMemberResp {

    @ApiModelProperty("uid")
    private Long uid;

    /**
     * @see com.gjq.planet.common.enums.blog.UserActiveStatusEnum
     */
    @ApiModelProperty("在线状态 1在线 2离线")
    private Integer activeStatus;

}
