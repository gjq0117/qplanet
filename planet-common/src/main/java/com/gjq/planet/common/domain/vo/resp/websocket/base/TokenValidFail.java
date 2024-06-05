package com.gjq.planet.common.domain.vo.resp.websocket.base;

import com.gjq.planet.common.enums.im.WSRespTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/15 19:22
 * @description: token验证失败，通知客户端重新登录
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidFail extends WsBaseResp {

    /**
     *  token
     */
    private String token;

    @Override
    public Integer getType() {
        return WSRespTypeEnum.TOKEN_VALID_FAIL.getType();
    }
}
