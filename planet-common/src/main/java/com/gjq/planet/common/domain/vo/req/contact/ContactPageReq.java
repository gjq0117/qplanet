package com.gjq.planet.common.domain.vo.req.contact;

import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: gjq0117
 * @date: 2024/5/25 13:24
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("会话列表分页请求")
public class ContactPageReq extends CursorPageBaseReq {
}
