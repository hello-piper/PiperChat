package piper.im.jsr.undertow.coder;

import com.alibaba.fastjson.JSONObject;
import piper.im.common.pojo.message.Message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author piper
 * @date 2020/9/11 17:11
 */
public class JsonDecode implements Decoder.Text<Message> {

    @Override
    public Message decode(String s) throws DecodeException {
        return JSONObject.parseObject(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
