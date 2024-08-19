package com.gjq.planet.blog.controller;

import com.gjq.planet.blog.service.IRobotService;
import com.gjq.planet.common.annotation.PlanetAdmin;
import com.gjq.planet.common.domain.vo.req.robot.RobotReq;
import com.gjq.planet.common.domain.vo.resp.robot.RobotResp;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.valid.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * 机器人信息 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-08-13
 */
@RestController
@RequestMapping("/robot")
@Api(tags = "机器人信息")
public class RobotController {

    @Autowired
    private IRobotService robotService;

    @PlanetAdmin
    @PostMapping("/getRobotList")
    @ApiOperation("获取机器人信息列表")
    public ApiResult<List<RobotResp>> getRobotList() {
        return ApiResult.success(robotService.getRobotList());
    }

    @PlanetAdmin
    @PostMapping("/add")
    @ApiOperation("新增一条机器人信息")
    public ApiResult<Void> add(@RequestBody @Valid RobotReq req) {
        robotService.add(req);
        return ApiResult.success();
    }

    @PlanetAdmin
    @PostMapping("/update")
    @ApiOperation("更新机器人信息")
    public ApiResult<Void> update(@RequestBody @Validated({UpdateGroup.class}) RobotReq req) {
        robotService.update(req);
        return ApiResult.success();
    }

    @PlanetAdmin
    @DeleteMapping("/delete/{robotId}")
    @ApiOperation("删除指定机器人信息")
    public ApiResult<Void> delete(@PathVariable("robotId") Long robotId) {
        robotService.deleteById(robotId);
        return ApiResult.success();
    }
}
