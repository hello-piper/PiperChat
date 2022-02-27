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

import io.piper.common.util.ThreadUtil;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OkHttpWebSocketClient {

    public static void main(String[] args) {
        int socketNum = 1000;

        String webSocketUrl = "ws://127.0.0.1:8080/echo";
        OkHttpClient client = new OkHttpClient.Builder().pingInterval(20, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(webSocketUrl).build();

        List<WebSocket> list = new ArrayList<>(socketNum);
        for (int i = 0; i < socketNum; i++) {
            list.add(i, client.newWebSocket(request, new Listener(i)));
        }

        ThreadUtil.SCHEDULE_POOL.scheduleAtFixedRate(() -> {
            for (WebSocket socket : list) {
                socket.send("PING");
            }
        }, 10000, 25000, TimeUnit.SECONDS);
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
            System.out.println("onMessage " + seq + " " + text);
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);
            System.out.println("onOpen " + seq);
        }
    }
}
