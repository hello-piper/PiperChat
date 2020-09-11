package org.example.coder;

import org.example.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author piper
 * @date 2020/9/11 16:51
 */
public class MsgpackEncode implements Encoder.Binary<Message> {

    @Override
    public ByteBuffer encode(Message message) throws EncodeException {
        try {
            return ByteBuffer.wrap(MessagePackConfig.MESSAGE_PACK.write(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
