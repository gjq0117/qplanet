package com.gjq.planet.common.domain.vo.req.userapply;

import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: gjq0117
 * @date: 2024/5/27 17:20
 * @description: 好友申请列表分页请求
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("好友申请列表分页请求")
@Data
public class UserApplyPageReq extends CursorPageBaseReq {

    @ApiModelProperty("当前用户id")
    private Long currUid;
}
