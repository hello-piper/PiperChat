package piper.im.repository.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanHandler;
import piper.im.common.dao.MessageDAO;
import piper.im.common.pojo.entity.Message;

import java.sql.SQLException;

/**
 * MessageDAOImpl
 *
 * @author piper
 */
public class MessageDAOImpl implements MessageDAO {

    @Override
    public Message getById(String id) {
        try {
            return Db.use().find(Entity.create("message").set("id", id), new BeanHandler<>(Message.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Message message) {
        int count = 0;
        try {
            count = Db.use().insert(Entity.parseWithUnderlineCase(message));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}
