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
package io.piper.im.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.common.pojo.req.RequestMsg;
import io.piper.common.util.StringUtil;

import java.util.Map;

/**
 * WebSocketFrameHandler
 *
 * @author piper
 */
@ChannelHandler.Sharable
public class WebSocketTextHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    protected static final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketTextHandler.class);
    private static final TextWebSocketFrame pong = new TextWebSocketFrame("pong");

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Long userKey = ImUserHolder.INSTANCE.getUserKey(ctx.channel());
        ImUserHolder.INSTANCE.removeSession(ctx.channel());
        if (userKey != null) {
            log.info("用户下线 {} {}", userKey, ImUserHolder.INSTANCE.onlineNum());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        String msg = frame.text();
        Channel channel = ctx.channel();
        Long userKey = ImUserHolder.INSTANCE.getUserKey(channel);
        log.info("receiveMsg {} {}", msg, userKey);
        try {
            if (StringUtil.isEmpty(msg)) {
                ImUserHolder.INSTANCE.close(channel);
                return;
            }
            if ("ping".equals(msg)) {
                channel.writeAndFlush(pong.retainedDuplicate());
                return;
            }
            RequestMsg requestMsg = JSONObject.parseObject(msg, RequestMsg.class);
            if (requestMsg.getType() == null || requestMsg.getData() == null || requestMsg.getData().isEmpty()) {
                ImUserHolder.INSTANCE.close(channel);
                return;
            }
            RequestMsg.RequestTypeEnum requestTypeEnum = RequestMsg.RequestTypeEnum.valueOf(requestMsg.getType());
            if (requestTypeEnum == null) {
                ImUserHolder.INSTANCE.close(channel);
                return;
            }
            if (RequestMsg.RequestTypeEnum.ENTER_ROOM == requestTypeEnum) {
                // 进入直播间
                Map<String, Object> data = requestMsg.getData();
                Long roomId = Long.valueOf(data.get("roomId").toString());
                ImUserHolder.INSTANCE.putRoomSession(roomId, channel);
            } else if (RequestMsg.RequestTypeEnum.EXIT_ROOM == requestTypeEnum) {
                // 退出直播间
                Map<String, Object> data = requestMsg.getData();
                Long roomId = Long.valueOf(data.get("roomId").toString());
                ImUserHolder.INSTANCE.removeRoomSession(roomId, channel);
            }
        } catch (Exception e) {
            log.error("receiveMsg {} {}", msg, userKey, e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Long userKey = ImUserHolder.INSTANCE.getUserKey(ctx.channel());
        log.error("{}", userKey, cause);
    }
}
