package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.service.IRoomService;
import com.gjq.planet.common.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 房间信息表 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@RestController
@RequestMapping("/room")
@Api(tags = "房间信息")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @ApiOperation("获取房间在线人数")
    @GetMapping("/getRoomOnlineNum/{roomId}")
    public ApiResult<Long> getRoomOnlineNum(@PathVariable("roomId") Long roomId) {
        return ApiResult.success(roomService.getOnlineNum(roomId));
    }
}

