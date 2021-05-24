package piper.im.jsr.undertow.coder;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import piper.im.common.pojo.message.Message;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.nio.ByteBuffer;

/**
 * @author piper
 * @date 2020/9/11 16:51
 */
public class ProtostuffDecode implements Decoder.Binary<Message> {

    @Override
    public Message decode(ByteBuffer byteBuffer) {
        Schema<Message> schema = RuntimeSchema.getSchema(Message.class);
        Message message = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(byteBuffer.array(), message, schema);
        return message;
    }

    @Override
    public boolean willDecode(ByteBuffer byteBuffer) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
