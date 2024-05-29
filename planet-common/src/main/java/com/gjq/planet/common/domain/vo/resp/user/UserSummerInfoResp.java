package com.gjq.planet.common.domain.vo.resp.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/23 11:39
 * @description: 用户聚合信息实体
 */
@ApiModel("用户聚合信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSummerInfoResp {

    @ApiModelProperty("uid")
    private Long uid;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty(value = "归属地")
    private String locPlace;

    @ApiModelProperty("最后更新时间")
    private Long lastUpdateTime;
}
