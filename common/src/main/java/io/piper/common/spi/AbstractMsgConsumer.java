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
package io.piper.common.spi;

import com.alibaba.fastjson.JSON;
import io.piper.common.constant.Constants;
import io.piper.common.db.RedisDS;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.pojo.config.ImProperties;
import io.piper.common.pojo.message.Msg;
import io.piper.common.util.ThreadUtil;
import io.piper.common.util.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.TimeUnit;

/**
 * AbstractMsgConsumer
 *
 * @author piper
 */
public abstract class AbstractMsgConsumer {
    static final Logger log = LoggerFactory.getLogger(AbstractMsgConsumer.class);
    private static final ImProperties imConfig = YamlUtil.getConfig("im", ImProperties.class);

    {
        if ("local".equals(YamlUtil.getProfile())) {
            ThreadUtil.SCHEDULE_POOL.scheduleWithFixedDelay(() -> {
                try {
                    Msg dto = Msg.createTextMsg("hello piper");
                    dto.setFrom(imConfig.getSystemUser());
                    dto.setTo(imConfig.getSystemRoom());
                    dto.setChatType(ChatTypeEnum.CHATROOM.type);
                    handler(dto);
                } catch (Exception ignored) {
                }
            }, 10, 10, TimeUnit.SECONDS);
        }
    }

    public AbstractMsgConsumer() {
        new Thread(() -> RedisDS.getJedis().subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                log.info("onMessage >>> {} {}", channel, message);
                handler(JSON.parseObject(message, Msg.class));
            }
        }, Constants.CHANNEL_IM_MESSAGE), "AbstractMsgConsumer Thread").start();
    }

    public abstract void handler(Msg msg);
}
