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
package io.piper.im.netty;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.spi.AbstractImUserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ImUserHolder
 *
 * @author piper
 */
public class ImUserHolder extends AbstractImUserHolder<Channel> {
    private static final Logger log = LoggerFactory.getLogger(ImUserHolder.class);

    public static final ImUserHolder INSTANCE = new ImUserHolder();

    @Override
    public Long getUserKey(Channel channel) {
        Attribute<Long> attr = channel.attr(AttributeKey.valueOf(USER_KEY));
        return attr.get();
    }

    @Override
    public UserTokenDTO getUserTokenDTO(Channel channel) {
        Attribute<UserTokenDTO> attr = channel.attr(AttributeKey.valueOf(USER_INFO));
        return attr.get();
    }

    @Override
    public void close(Channel channel) {
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                log.info("sessionClose error {}", getUserKey(channel), e);
            }
        }
    }

}
