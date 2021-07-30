/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.server.spring.controller;

import io.piper.common.exception.IMResult;
import io.piper.common.pojo.config.AddressInfo;
import io.piper.common.pojo.message.Msg;
import io.piper.server.spring.dto.ImMessageDTO;
import io.piper.server.spring.dto.page_dto.MsgSearchDTO;
import io.piper.server.spring.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "聊天接口")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    @GetMapping("im-server")
    @ApiOperation("获取IM服务地址")
    public IMResult<AddressInfo> getAddress() {
        return IMResult.ok(chatService.getAddress());
    }

    @PostMapping("send")
    @ApiOperation("发消息")
    public IMResult<Boolean> chat(@RequestBody Msg msg) {
        return IMResult.ok(chatService.chat(msg));
    }

    @PostMapping("records")
    @ApiOperation("聊天记录")
    public IMResult<List<ImMessageDTO>> chatRecord(@RequestBody MsgSearchDTO searchDTO) {
        return IMResult.ok(chatService.chatRecord(searchDTO));
    }
}
