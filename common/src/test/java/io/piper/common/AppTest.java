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
package io.piper.common;

import org.junit.Test;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import io.piper.common.pojo.message.protoObj.FileMsgBody;
import io.piper.common.pojo.message.protoObj.Msg;
import io.piper.common.pojo.message.protoObj.PBOuterClass;

public class AppTest {

    @Test
    public void protobufTest() throws InvalidProtocolBufferException {
        JsonFormat.TypeRegistry typeRegistry = JsonFormat.TypeRegistry.newBuilder().add(PBOuterClass.getDescriptor().getMessageTypes()).build();

        JsonFormat.Parser parser = JsonFormat.parser().usingTypeRegistry(typeRegistry);
        JsonFormat.Printer printer = JsonFormat.printer().usingTypeRegistry(typeRegistry);

        Msg.Builder builder = Msg.newBuilder();
        builder.setId(1);
        builder.setType(1);
        builder.setChatType(1);

        FileMsgBody.Builder fileMsgBodyBuilder = FileMsgBody.newBuilder();
        fileMsgBodyBuilder.setName("file");
        fileMsgBodyBuilder.setUrl("url");
        fileMsgBodyBuilder.setSize(100);
        builder.setFileMsgBody(fileMsgBodyBuilder);

        Msg msg = builder.build();

        System.out.println(msg.getSerializedSize());

        System.out.println(printer.print(msg));

        builder.clearFileMsgBody();
        builder.setBody(Any.pack(fileMsgBodyBuilder.build()));
        msg = builder.build();

        System.out.println(msg.getSerializedSize());

        System.out.println(printer.print(msg));

        Any body = msg.getBody();
        if (body.is(FileMsgBody.class)) {
            FileMsgBody unpack = body.unpack(FileMsgBody.class);
        }

    }
}
