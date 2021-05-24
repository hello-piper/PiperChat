package piper.im.jsr.undertow;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;
import piper.im.common.enums.ChatTypeEnum;
import piper.im.common.pojo.message.Message;

public class AppTest {

    @Test
    public void testProtostuff() {
        Message message = Message.createTextMessage("hello protostuff");
        message.setChatType(ChatTypeEnum.SINGLE.type);
        message.setFrom("1");
        message.setTo("2");

        Schema<Message> schema = RuntimeSchema.getSchema(Message.class);

        // Re-use (manage) this buffer to avoid allocating on every serialization
        LinkedBuffer buffer = LinkedBuffer.allocate(512);

        // ser
        final byte[] protostuff;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(message, schema, buffer);
        } finally {
            buffer.clear();
        }

        // deser
        Message fooParsed = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(protostuff, fooParsed, schema);
    }
}
