package com.gjq.planet.common.domain.vo.resp.websocket.base;

import com.gjq.planet.common.domain.vo.resp.contact.ContactResp;
import com.gjq.planet.common.enums.im.WSRespTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/6/15 13:14
 * @description: 新会话通知
 */

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewContact extends WsBaseResp {

    /**
     *  会话响应实体
     */
    private ContactResp contactResp;

    @Override
    public Integer getType() {
        return WSRespTypeEnum.NEW_CONTACT.getType();
    }
}
