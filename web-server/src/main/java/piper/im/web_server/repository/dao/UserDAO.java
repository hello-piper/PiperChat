package piper.im.web_server.repository.dao;

import piper.im.common.pojo.entity.User;

public interface UserDAO {

    User getById(String id);

    boolean insert(User user);
}
