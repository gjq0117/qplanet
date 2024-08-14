package com.gjq.planet.common.domain.vo.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author: gjq0117
 * @date: 2024/5/23 14:27
 * @description: 游标翻页请求
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("游标翻页请求")
public class CursorPageBaseReq {

    @ApiModelProperty("页面大小")
    @Min(0)
    @Max(100)
    private Integer pageSize = 10;

    @ApiModelProperty("游标（初始为null，后续请求附带上次翻页的游标）")
    private String cursor;

    public Page initPage() {
        return new Page(1,this.pageSize,false);
    }
}
