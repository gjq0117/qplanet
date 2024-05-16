package com.gjq.planet.common.domain.dto.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/15 15:53
 * @description: ws连接的一些额外参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSChannelExtraDTO {

    /**
     *  用户ID
     */
    private Long uid;
}
