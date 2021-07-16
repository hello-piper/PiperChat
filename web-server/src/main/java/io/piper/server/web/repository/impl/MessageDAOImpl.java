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
package io.piper.server.web.repository.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanHandler;
import io.piper.common.pojo.entity.ImMessage;
import io.piper.server.web.repository.dao.MessageDAO;

import java.sql.SQLException;

/**
 * MessageDAOImpl
 *
 * @author piper
 */
public class MessageDAOImpl implements MessageDAO {

    @Override
    public ImMessage getById(String id) {
        try {
            return Db.use().find(Entity.create("message").set("id", id), new BeanHandler<>(ImMessage.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(ImMessage imMessage) {
        int count = 0;
        try {
            count = Db.use().insert(Entity.parseWithUnderlineCase(imMessage));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}
