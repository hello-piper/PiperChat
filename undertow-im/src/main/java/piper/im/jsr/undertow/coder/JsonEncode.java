package piper.im.jsr.undertow.coder;

import cn.hutool.json.JSONUtil;
import piper.im.common.pojo.message.Msg;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author piper
 * @date 2020/9/11 17:11
 */
public class JsonEncode implements Encoder.Text<Msg> {

    @Override
    public String encode(Msg msg) throws EncodeException {
        return JSONUtil.toJsonStr(msg);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
