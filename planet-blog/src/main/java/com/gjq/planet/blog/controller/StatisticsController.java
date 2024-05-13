package com.gjq.planet.blog.controller;

import com.gjq.planet.blog.annotation.PlanetAdmin;
import com.gjq.planet.blog.service.StatisticsService;
import com.gjq.planet.blog.utils.ApiResult;
import com.gjq.planet.common.domain.vo.resp.statistics.GenderRateResp;
import com.gjq.planet.common.domain.vo.resp.statistics.KeyBroadResp;
import com.gjq.planet.common.domain.vo.resp.statistics.WebRecentVisitResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: gjq0117
 * @date: 2024/5/3 17:24
 * @description: 统计接口
 */
@RestController
@RequestMapping("/statistics")
@Api(tags = "统计接口",value = "统计接口")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @PlanetAdmin
    @GetMapping("/getKeyBroadData")
    @ApiOperation("统计-获取关键数据")
    public ApiResult<KeyBroadResp> getKeyBroadData() {
        return ApiResult.success(statisticsService.getKeyBroadData());
    }

    @PlanetAdmin
    @ApiOperation("网站近七日访问量")
    @GetMapping("/sevenDayVisitCount")
    public ApiResult<List<WebRecentVisitResp>> sevenDayVisitCount() {
        return ApiResult.success(statisticsService.sevenDayVisitCount());
    }

    @PlanetAdmin
    @ApiOperation("获取男女比例")
    @GetMapping("/getGenderRate")
    public ApiResult<List<GenderRateResp>> getGenderRate() {
        return ApiResult.success(statisticsService.getGenderRate());
    }
}
