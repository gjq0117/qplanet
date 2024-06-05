package com.gjq.planet.common.domain.vo.resp.contact;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: gjq0117
 * @date: 2024/5/25 13:18
 * @description: 会话响应实体
 */
@ApiModel("会话响应实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactResp {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("房间id")
    private Long roomId;

    @ApiModelProperty("最后阅读的时间")
    private Date readTime;

    @ApiModelProperty("最后活跃时间")
    private String activeTime;

    @ApiModelProperty("最后一条消息")
    private String lastMsg;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("群名/好友名")
    private String name;

    @ApiModelProperty("最后一条消息发送人姓名")
    private String lastMsgSendName;
}
