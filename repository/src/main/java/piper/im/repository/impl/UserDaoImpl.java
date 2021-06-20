package piper.im.repository.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanHandler;
import piper.im.common.dao.UserDao;
import piper.im.common.pojo.entity.User;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    @Override
    public User getById(String id) throws SQLException {
        return Db.use().find(Entity.create("user").set("id", id), new BeanHandler<>(User.class));
    }

    @Override
    public boolean insert(User user) throws SQLException {
        int count = Db.use().insert(Entity.parseWithUnderlineCase(user));
        return count > 0;
    }
}
