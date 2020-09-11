package org.example;

import org.example.coder.MessagePackConfig;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

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
