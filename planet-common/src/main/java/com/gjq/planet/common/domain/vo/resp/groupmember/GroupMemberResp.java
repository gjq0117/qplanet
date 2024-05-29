package com.gjq.planet.common.domain.vo.resp.groupmember;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/24 20:35
 * @description: 成员响应信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("成员响应实体（这里只要传用户ID过去，用户聚合信息等前端进行懒加载）")
public class GroupMemberResp {

    @ApiModelProperty("用户ID")
    private Long uid;

    @ApiModelProperty("在线状态")
    private Integer isActive;
}
