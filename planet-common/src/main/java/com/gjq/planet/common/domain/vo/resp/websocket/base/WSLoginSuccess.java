package com.gjq.planet.common.domain.vo.resp.websocket.base;

import com.gjq.planet.common.enums.im.WSRespTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: gjq0117
 * @date: 2024/5/15 18:23
 * @description: 登录成功通知
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WSLoginSuccess extends WsBaseResp {

    /**
     *  用户ID
     */
    private Long uid;

    @Override
    public Integer getType() {
        return WSRespTypeEnum.LOGIN_SUCCESS.getType();
    }
}
