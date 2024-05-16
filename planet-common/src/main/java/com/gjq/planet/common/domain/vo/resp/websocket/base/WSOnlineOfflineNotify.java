package com.gjq.planet.common.domain.vo.resp.websocket.base;

import com.gjq.planet.common.enums.websocket.WSRespTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/15 18:26
 * @description: 用户上下线通知
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户上下线通知")
public class WSOnlineOfflineNotify extends BaseResp{

    private static final Integer type = WSRespTypeEnum.ONLINE_OFFLINE_NOTIFY.getType();

    @ApiModelProperty("新的上下线用户")
    private List<ChatMemberResp> changeList = new ArrayList<>();

    @ApiModelProperty("在线人数")
    private Long onlineNum;

    @Override
    public BaseResp buildResp(Object... o) {
        return null;
    }

    @Override
    public Integer getType() {
        return 0;
    }
}
