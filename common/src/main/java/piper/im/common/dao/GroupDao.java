package piper.im.common.dao;

import piper.im.common.pojo.entity.Group;
import piper.im.common.pojo.entity.User;

import java.sql.SQLException;

public interface GroupDao {

    Group getById(String id) throws SQLException;

    boolean insert(Group group) throws SQLException;
}
