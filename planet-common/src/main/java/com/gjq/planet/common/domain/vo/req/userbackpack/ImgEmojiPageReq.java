package com.gjq.planet.common.domain.vo.req.userbackpack;

import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: gjq0117
 * @date: 2024/7/6 21:32
 * @description: 表情包分页请求
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("表情包分页请求")
@Data
public class ImgEmojiPageReq extends CursorPageBaseReq {
}
