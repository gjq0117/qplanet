package com.gjq.planet.common.domain.vo.resp.websocket;

import com.gjq.planet.common.domain.vo.resp.websocket.base.WsBaseResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

;

/**
 * @author: gjq0117
 * @date: 2024/5/15 18:19
 * @description: ws响应消息类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ws响应消息类")
public class WSResp {

    public WSResp(WsBaseResp data) {
        this.data = data;
        this.type = data.getType();
    }

    /**
     * @see com.gjq.planet.common.enums.im.WSRespTypeEnum
     */
    @ApiModelProperty("响应类型")
    private Integer type;

    @ApiModelProperty("数据类型")
    private WsBaseResp data;
}
