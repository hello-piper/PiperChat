package org.example.coder;

import com.alibaba.fastjson.JSONObject;
import org.example.Message;

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
