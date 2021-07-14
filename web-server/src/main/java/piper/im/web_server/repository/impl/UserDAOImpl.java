package piper.im.web_server.repository.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanHandler;
import piper.im.common.pojo.entity.ImUser;
import piper.im.web_server.repository.dao.UserDAO;

import java.sql.SQLException;

/**
 * UserDAOImpl
 *
 * @author piper
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public ImUser getById(String id) {
        try {
            return Db.use().find(Entity.create("user").set("id", id), new BeanHandler<>(ImUser.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(ImUser imUser) {
        int count = 0;
        try {
            count = Db.use().insert(Entity.parseWithUnderlineCase(imUser));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}
