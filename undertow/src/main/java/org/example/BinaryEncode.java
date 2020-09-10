package org.example;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.nio.ByteBuffer;

public class BinaryEncode implements Encoder.Binary<Message> {

    @Override
    public ByteBuffer encode(Message message) throws EncodeException {
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
