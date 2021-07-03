package piper.im.jsr.undertow;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;
import piper.im.common.enums.ChatTypeEnum;
import piper.im.common.pojo.message.Msg;

public class AppTest {

    @Test
    public void testProtostuff() {
        Msg msg = Msg.createTextMsg("hello protostuff");
        msg.setChatType(ChatTypeEnum.SINGLE.type);
        msg.setFrom("1");
        msg.setTo("2");

        Schema<Msg> schema = RuntimeSchema.getSchema(Msg.class);

        // Re-use (manage) this buffer to avoid allocating on every serialization
        LinkedBuffer buffer = LinkedBuffer.allocate(512);

        // ser
        final byte[] protostuff;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(msg, schema, buffer);
        } finally {
            buffer.clear();
        }

        // deser
        Msg fooParsed = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(protostuff, fooParsed, schema);
    }
}
