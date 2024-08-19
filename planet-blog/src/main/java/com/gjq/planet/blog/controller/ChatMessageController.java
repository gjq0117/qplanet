package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.service.IMessageService;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessagePageReq;
import com.gjq.planet.common.domain.vo.req.chat.ChatMessageReq;
import com.gjq.planet.common.domain.vo.req.robot.CallRobotReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;
import com.gjq.planet.common.domain.vo.resp.chat.ChatMessageBody;
import com.gjq.planet.common.utils.ApiResult;
import com.gjq.planet.common.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 聊天消息 前端控制器
 * </p>
 *
 * @author gjq
 * @since 2024-05-24
 */
@RestController
@RequestMapping("/chat/message")
@Api(tags = "聊天消息接口")
public class ChatMessageController {

    @Autowired
    private IMessageService chatService;


    @ApiOperation(("聊天列表分页请求"))
    @PostMapping("/pageMsg")
    public ApiResult<CursorPageBaseResp<ChatMessageBody>> pageMsg(@RequestBody @Valid ChatMessagePageReq req) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(chatService.PageMsg(req, uid));
    }


    @ApiOperation("发送消息")
    @PostMapping("/sendMsg")
    public ApiResult<ChatMessageBody> sendMsg(@RequestBody @Valid ChatMessageReq req) {
        Long msgId = chatService.sendMsg(RequestHolder.get().getUid(), req);
        return ApiResult.success(chatService.buildMsgResp(msgId));
    }

    @ApiOperation("请求机器人")
    @PostMapping("/callRobot")
    public ApiResult<ChatMessageBody> callRobot(@RequestBody @Valid CallRobotReq req) {
        Long msgId = chatService.callRobot(RequestHolder.get().getUid(),req);
        return ApiResult.success(chatService.buildMsgResp(msgId));
    }



}

