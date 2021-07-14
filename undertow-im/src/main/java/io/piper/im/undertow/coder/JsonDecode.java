package io.piper.im.undertow.coder;

import cn.hutool.json.JSONUtil;
import io.piper.common.pojo.message.Msg;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author piper
 * @date 2020/9/11 17:11
 */
public class JsonDecode implements Decoder.Text<Msg> {

    @Override
    public Msg decode(String s) throws DecodeException {
        return JSONUtil.toBean(s, Msg.class);
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
