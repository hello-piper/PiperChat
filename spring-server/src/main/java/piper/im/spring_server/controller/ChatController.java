package piper.im.spring_server.controller;

import piper.im.spring_server.service.ChatService;
import org.springframework.web.bind.annotation.*;
import piper.im.common.exception.IMResult;
import piper.im.common.pojo.config.AddressInfo;
import piper.im.common.pojo.message.Msg;

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
