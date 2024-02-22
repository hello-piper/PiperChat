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
package io.piper.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;

import io.piper.common.pojo.message.protoObj.Msg;
import io.piper.common.util.ThreadUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class OkHttpWebSocketClient {

    public static void main(String[] args) {
        int socketNum = 1;

        String webSocketUrl = "ws://127.0.0.1:8080/websocket/guest";
        OkHttpClient client = new OkHttpClient.Builder().pingInterval(30, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(webSocketUrl).build();

        List<WebSocket> list = new ArrayList<>(socketNum);
        for (int i = 0; i < socketNum; i++) {
            list.add(i, client.newWebSocket(request, new Listener(i)));
        }

        Msg.Builder builder = Msg.newBuilder();
        builder.setType(0);
        builder.setChatType(0);
        builder.setFrom(0);
        builder.addAllTo(Collections.singletonList(0L));
        builder.setText("Hi");
        Msg msg = builder.build();

        ThreadUtil.SCHEDULE_POOL.scheduleAtFixedRate(() -> {
            for (WebSocket socket : list) {
                socket.send("ping");
                socket.send(new ByteString(msg.toByteArray()));
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

    static class Listener extends WebSocketListener {
        private final int seq;

        Listener(int seq) {
            this.seq = seq;
        }

        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosed(webSocket, code, reason);
            System.out.println("onClosed " + seq);
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            super.onMessage(webSocket, text);
            System.out.println("onMessage text " + seq + " " + text);
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            super.onMessage(webSocket, bytes);
            try {
                Msg msg = Msg.parseFrom(bytes.toByteArray());
                System.out.println("onMessage bytes " + seq + " " + bytes + "  " + msg.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);
            System.out.println("onOpen " + seq);
        }
    }
}
