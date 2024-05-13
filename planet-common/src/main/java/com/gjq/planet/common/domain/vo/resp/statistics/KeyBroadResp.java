package com.gjq.planet.common.domain.vo.resp.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gjq0117
 * @date: 2024/5/3 18:11
 * @description:
 */
@ApiModel("统计-基本数据响应实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyBroadResp {

    @ApiModelProperty("用户量")
    private Integer userCount;

    @ApiModelProperty("文章数")
    private Integer articleCount;

    @ApiModelProperty("网站总浏览量")
    private Long viewCount;

    @ApiModelProperty("累计点赞量")
    private Integer likeCount;
}
