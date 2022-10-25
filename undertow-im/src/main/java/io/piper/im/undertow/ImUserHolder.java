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
package io.piper.im.undertow;

import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.spi.AbstractImUserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;

/**
 * ImUserHolder
 *
 * @author piper
 */
public class ImUserHolder extends AbstractImUserHolder<Session> {
    private static final Logger log = LoggerFactory.getLogger(ImUserHolder.class);

    public ImUserHolder() {
        INSTANCE = this;
    }

    @Override
    public AbstractImUserHolder<Session> getInstance() {
        if (INSTANCE == null) {
            synchronized (ImUserHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImUserHolder();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Long getUserKey(Session session) {
        return (Long) session.getUserProperties().get(ImUserHolder.USER_KEY);
    }

    @Override
    public UserTokenDTO getUserTokenDTO(Session session) {
        return (UserTokenDTO) session.getUserProperties().get(ImUserHolder.USER_INFO);
    }

    @Override
    public void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                log.info("sessionClose error {}", session.getUserProperties(), e);
            }
        }
    }

}
