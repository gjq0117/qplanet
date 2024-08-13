package com.gjq.planet.common.domain.vo.req.robot;

import com.gjq.planet.common.valid.YesOrNo;
import com.gjq.planet.common.valid.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 机器人 新增/修改请求体
 * </p>
 *
 * @author gjq
 * @since 2024-08-13
 */
@ApiModel("机器人信息请求实体")
@Data
public class RobotReq {

    @ApiModelProperty("机器人ID")
    @NotNull(message = "id不能为空", groups = {UpdateGroup.class})
    private Long id;

    /**
     *  机器人名
     */
    @ApiModelProperty("机器人名")
    @NotBlank(message = "机器人名不能为空")
    private String name;

    /**
     *  头像
     */
    @ApiModelProperty("头像")
    @NotBlank(message = "头像不能为空")
    private String avatar;

    /**
     *  大模型
     */
    @ApiModelProperty("大模型")
    @NotBlank(message = "模型不能为空")
    private String model;

    /**
     *  模型的温度（0~1之间）
     */
    @ApiModelProperty("模型的温度（0~1之间）")
    @NotNull(message = "模型温度不能为空")
    @Min(value = 0, message = "模型温度不能小于0")
    @Max(value = 1, message = "模型温度不能大于1")
    private Float temperature;

    /**
     *  api_key
     */
    @ApiModelProperty("api_key")
    @NotBlank(message = "api_key不能为空")
    private String apiKey;

    /**
     *  base_url
     */
    @ApiModelProperty("base_url")
    @NotBlank(message = "base_url不能为空")
    private String baseUrl;

    @ApiModelProperty("是否启用 【0：否；1：是】")
    @YesOrNo
    private Integer enabled;
}
