package piper.im.common.dao;

import piper.im.common.pojo.entity.User;

import java.sql.SQLException;

public interface UserDao {

    User getById(String id) throws SQLException;

    boolean insert(User user) throws SQLException;
}
