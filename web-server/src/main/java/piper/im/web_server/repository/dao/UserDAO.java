package piper.im.web_server.repository.dao;

import piper.im.common.pojo.entity.ImUser;

public interface UserDAO {

    ImUser getById(String id);

    boolean insert(ImUser imUser);
}
