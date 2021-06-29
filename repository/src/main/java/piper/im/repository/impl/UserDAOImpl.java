package piper.im.repository.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanHandler;
import piper.im.common.dao.UserDAO;
import piper.im.common.pojo.entity.User;

import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getById(String id) {
        try {
            return Db.use().find(Entity.create("user").set("id", id), new BeanHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(User user) {
        int count = 0;
        try {
            count = Db.use().insert(Entity.parseWithUnderlineCase(user));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count > 0;
    }
}
