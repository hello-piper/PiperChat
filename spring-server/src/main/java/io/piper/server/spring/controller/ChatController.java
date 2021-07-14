package io.piper.server.spring.controller;

import io.piper.server.spring.service.ChatService;
import org.springframework.web.bind.annotation.*;
import io.piper.common.exception.IMResult;
import io.piper.common.pojo.config.AddressInfo;
import io.piper.common.pojo.message.Msg;

import javax.annotation.Resource;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    @GetMapping
    public IMResult<AddressInfo> getAddress() {
        return IMResult.ok(chatService.getAddress());
    }

    @PostMapping
    public IMResult<Void> chat(@RequestBody Msg msg) {
        chatService.chat(msg);
        return IMResult.ok();
    }
}
