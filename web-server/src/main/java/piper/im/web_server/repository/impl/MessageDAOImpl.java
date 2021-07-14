package piper.im.web_server.repository.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanHandler;
import piper.im.common.pojo.entity.ImMessage;
import piper.im.web_server.repository.dao.MessageDAO;

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
