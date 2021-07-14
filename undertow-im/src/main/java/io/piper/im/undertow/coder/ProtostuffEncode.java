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
