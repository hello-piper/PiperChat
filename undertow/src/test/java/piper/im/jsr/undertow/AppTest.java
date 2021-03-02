package piper.im.jsr.undertow;

import org.junit.Test;
import piper.im.common.enums.ChatTypeEnum;
import piper.im.common.pojo.message.Message;
import piper.im.jsr.undertow.coder.MessagePackConfig;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void testMsgpack() throws IOException {
        Message message = Message.createTextMessage("hello msgpack");
        message.setChatType(ChatTypeEnum.SINGLE.type);
        message.setFrom("1");
        message.setTo("2");

        byte[] write = MessagePackConfig.MESSAGE_PACK.write(message);
        Message read = MessagePackConfig.MESSAGE_PACK.read(write, Message.class);

        assertEquals(message.toString(), read.toString());
    }
}
