package piper.im.jsr.undertow.coder;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import piper.im.common.pojo.message.Message;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.nio.ByteBuffer;

/**
 * @author piper
 * @date 2020/9/11 16:51
 */
public class ProtostuffEncode implements Encoder.Binary<Message> {
    static LinkedBuffer buffer = LinkedBuffer.allocate(1024);

    @Override
    public ByteBuffer encode(Message message) {
        Schema<Message> schema = RuntimeSchema.getSchema(Message.class);
        try {
            return ByteBuffer.wrap(ProtostuffIOUtil.toByteArray(message, schema, buffer));
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
