package com.gjq.planet.blog.controller;


import com.gjq.planet.blog.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ChatMessageController {

    @Autowired
    private IMessageService chatService;

}

