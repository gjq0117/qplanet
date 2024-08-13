package com.gjq.planet.common.domain.vo.resp.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;

/**
 * @ClassDescription: 机器人分页信息返回实体
 * @Author: gjq
 * @Created: 2024-08-13 16:41
 */
@ApiModel("机器人分页信息返回实体")
@Data
public class RobotResp {

    @ApiModelProperty("机器人ID")
    private Long id;

    /**
     *  机器人名
     */
    @ApiModelProperty("机器人名")
    private String name;

    /**
     *  头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     *  大模型
     */
    @ApiModelProperty("大模型")
    private String model;

    /**
     *  模型的温度（0~1之间）
     */
    @ApiModelProperty("模型的温度（0~1之间）")
    private Float temperature;

    /**
     *  api_key
     */
    @ApiModelProperty("api_key")
    private String apiKey;

    /**
     *  base_url
     */
    @ApiModelProperty("base_url")
    private String baseUrl;

    /**
     *  今日回答次数
     */
    @ApiModelProperty("今日回答次数")
    private Integer todayReplyNum;

    /**
     *  总共回答次数
     */
    @ApiModelProperty("总共回答次数")
    private Integer totalReplyNum;

    /**
     *  每日上限的回答次数
     */
    @ApiModelProperty("每日上限的回答次数")
    private Integer dailyLimitNum;

    /**
     *  今日失败次数
     */
    @ApiModelProperty("今日失败次数")
    private Integer todayFailNum;

    /**
     *  总共失败次数
     */
    @ApiModelProperty("总共失败次数")
    private Integer totalFailNum;

    /**
     *  是否启用 【0：否；1：是】
     */
    @ApiModelProperty("是否启用 【0：否；1：是】")
    private Integer enabled;

    /**
     *  响应成功率
     */
    @ApiModelProperty("响应成功率")
    private Float successRate;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 最后更新时间
     */
    @ApiModelProperty("最后更新时间")
    private Date updateTime;

}
