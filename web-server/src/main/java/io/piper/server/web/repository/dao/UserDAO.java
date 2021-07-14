package io.piper.server.web.repository.dao;

import io.piper.common.pojo.entity.ImUser;

public interface UserDAO {

    ImUser getById(String id);

    boolean insert(ImUser imUser);
}
