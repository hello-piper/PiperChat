package piper.im.jsr.undertow;

import org.junit.Test;
import piper.im.jsr.undertow.coder.MessagePackConfig;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void testMsgpack() throws IOException {
        Message message = new Message();
        message.setType((byte) 0);
        message.setContent("hello msgpack");

        byte[] write = MessagePackConfig.MESSAGE_PACK.write(message);
        Message read = MessagePackConfig.MESSAGE_PACK.read(write, Message.class);

        assertEquals(message.toString(), read.toString());
    }
}
