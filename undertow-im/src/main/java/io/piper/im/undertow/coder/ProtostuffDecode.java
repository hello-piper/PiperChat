package io.piper.im.undertow.coder;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.piper.common.pojo.message.Msg;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.nio.ByteBuffer;

/**
 * @author piper
 * @date 2020/9/11 16:51
 */
public class ProtostuffDecode implements Decoder.Binary<Msg> {

    @Override
    public Msg decode(ByteBuffer byteBuffer) {
        Schema<Msg> schema = RuntimeSchema.getSchema(Msg.class);
        Msg msg = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(byteBuffer.array(), msg, schema);
        return msg;
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
