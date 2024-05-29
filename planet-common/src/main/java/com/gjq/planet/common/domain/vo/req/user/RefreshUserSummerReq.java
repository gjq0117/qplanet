package com.gjq.planet.common.domain.vo.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/25 22:02
 * @description: 用户聚合信息刷新请求
 */
@Data
@ApiModel("用户聚合信息刷新请求")
public class RefreshUserSummerReq {

    @ApiModelProperty("需要刷新的信息")
    private List<InfoReq> reqList;

    @Data
    public static class InfoReq {
        @ApiModelProperty("uid")
        private Long uid;

        @ApiModelProperty("最后刷新时间")
        private Long lastRefreshTime;
    }
}
