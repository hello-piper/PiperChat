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
import io.netty.util.ReferenceCountUtil;
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
        String userKey = UserSessionHolder.getUserKey(ctx.channel());
        UserSessionHolder.removeSession(ctx.channel());
        if (userKey != null) {
            log.info("用户下线 {} {}", userKey, UserSessionHolder.onlineNum());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        String msg = frame.text();
        Channel channel = ctx.channel();
        String userKey = UserSessionHolder.getUserKey(channel);
        log.info("receiveMsg {} {}", msg, userKey);
        try {
            if (StringUtil.isEmpty(msg)) {
                UserSessionHolder.close(channel);
                return;
            }
            if ("ping".equals(msg)) {
                channel.write(pong);
                return;
            }
            RequestMsg requestMsg = JSONObject.parseObject(msg, RequestMsg.class);
            if (requestMsg.getType() == null || requestMsg.getData() == null || requestMsg.getData().isEmpty()) {
                UserSessionHolder.close(channel);
                return;
            }
            RequestMsg.RequestTypeEnum requestTypeEnum = RequestMsg.RequestTypeEnum.valueOf(requestMsg.getType());
            if (requestTypeEnum == null) {
                UserSessionHolder.close(channel);
                return;
            }
            if (RequestMsg.RequestTypeEnum.ENTER_ROOM == requestTypeEnum) {
                // 进入直播间
                Map<String, Object> data = requestMsg.getData();
                String roomId = String.valueOf(data.get("roomId"));
                UserSessionHolder.putRoomSession(roomId, channel);
            } else if (RequestMsg.RequestTypeEnum.EXIT_ROOM == requestTypeEnum) {
                // 退出直播间
                Map<String, Object> data = requestMsg.getData();
                String roomId = String.valueOf(data.get("roomId"));
                UserSessionHolder.removeRoomSession(roomId, channel);
            }
        } catch (Exception e) {
            log.error("receiveMsg {} {}", msg, userKey, e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        String userKey = UserSessionHolder.getUserKey(ctx.channel());
        log.error("{}", userKey, cause);
    }
}
