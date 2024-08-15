package com.gjq.planet.common.domain.vo.resp.websocket.base;

import com.gjq.planet.common.enums.im.WSRespTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassDescription: 机器人回调通知
 * @Author: gjq
 * @Created: 2024-08-14 18:35
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallRobot extends WsBaseResp {

    /**
     *  回调机器人的ID
     */
    private Long robotId;

    /**
     *  机器人需要回答的消息ID
     */
    private Long msgId;

    /**
     *  房间号
     */
    private Long roomId;

    @Override
    public Integer getType() {
        return WSRespTypeEnum.CALL_ROBOT.getType();
    }
}
