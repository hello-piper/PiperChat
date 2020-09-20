package piper.im.jsr.undertow.coder;

import com.alibaba.fastjson.JSONObject;
import piper.im.jsr.undertow.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author piper
 * @date 2020/9/11 17:11
 */
public class JsonEncode implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) throws EncodeException {
        return JSONObject.toJSONString(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
