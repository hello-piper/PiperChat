package piper.im.jsr.undertow.coder;

import piper.im.common.pojo.message.Message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author piper
 * @date 2020/9/11 16:51
 */
public class MsgpackDecode implements Decoder.Binary<Message> {

    @Override
    public Message decode(ByteBuffer byteBuffer) throws DecodeException {
        try {
            return MessagePackConfig.MESSAGE_PACK.read(byteBuffer.array(), Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
