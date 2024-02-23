/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.im.undertow.coder;

import java.nio.ByteBuffer;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.protobuf.InvalidProtocolBufferException;

import io.piper.common.pojo.message.protoObj.Msg;

/**
 * ProtobufDecode
 * @author piper
 */
public class ProtobufDecode implements Decoder.Binary<Msg> {

    @Override
    public Msg decode(ByteBuffer byteBuffer) throws DecodeException {
        try {
            return Msg.parseFrom(byteBuffer);
        } catch (InvalidProtocolBufferException e) {
            throw new DecodeException(byteBuffer, e.getMessage(), e);
        }
    }

    @Override
    public boolean willDecode(ByteBuffer byteBuffer) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
