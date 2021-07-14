package piper.im.web_server.repository.dao;

import piper.im.common.pojo.entity.Message;

public interface MessageDAO {

    Message getById(String id);

    boolean insert(Message message);
}
