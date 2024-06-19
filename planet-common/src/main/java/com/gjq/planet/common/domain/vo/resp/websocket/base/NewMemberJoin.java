package com.gjq.planet.common.domain.vo.resp.websocket.base;

import com.gjq.planet.common.enums.im.WSRespTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/6/8 10:39
 * @description: 新人进群通知
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewMemberJoin extends WsBaseResp {

    /**
     *  新成员ID
     */
    private Long uid;

    @Override
    public Integer getType() {
        return WSRespTypeEnum.NEW_MEMBERS_JOINING_GROUP.getType();
    }
}
