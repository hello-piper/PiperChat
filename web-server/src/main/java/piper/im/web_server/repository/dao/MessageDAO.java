package piper.im.web_server.repository.dao;

import piper.im.common.pojo.entity.ImMessage;

public interface MessageDAO {

    ImMessage getById(String id);

    boolean insert(ImMessage imMessage);
}
