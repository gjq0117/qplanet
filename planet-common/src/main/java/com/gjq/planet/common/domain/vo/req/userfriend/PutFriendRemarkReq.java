package com.gjq.planet.common.domain.vo.req.userfriend;

import com.gjq.planet.common.valid.RegexpConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author: gjq0117
 * @date: 2024/5/29 13:33
 * @description: 设置好友备注请求
 */
@ApiModel("设置好友备注请求")
@Data
public class PutFriendRemarkReq {

    @ApiModelProperty("好友Id")
    private Long uid;

    @ApiModelProperty("备注")
    @Pattern(regexp = RegexpConstant.FRIEND_REMARK_REGEXP, message = RegexpConstant.FRIEND_REMARK_MASSAGE)
    private String remark;
}
