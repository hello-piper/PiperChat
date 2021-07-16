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
package io.piper.im.undertow.coder;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.piper.common.pojo.message.Msg;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.nio.ByteBuffer;

/**
 * ProtostuffEncode
 *
 * @author piper
 * @date 2020/9/11 16:51
 */
public class ProtostuffEncode implements Encoder.Binary<Msg> {
    static LinkedBuffer buffer = LinkedBuffer.allocate(1024);

    @Override
    public ByteBuffer encode(Msg msg) {
        Schema<Msg> schema = RuntimeSchema.getSchema(Msg.class);
        try {
            return ByteBuffer.wrap(ProtostuffIOUtil.toByteArray(msg, schema, buffer));
        } finally {
            buffer.clear();
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
