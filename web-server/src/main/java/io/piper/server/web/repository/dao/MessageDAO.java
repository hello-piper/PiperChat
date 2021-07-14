package io.piper.server.web.repository.dao;

import io.piper.common.pojo.entity.ImMessage;

public interface MessageDAO {

    ImMessage getById(String id);

    boolean insert(ImMessage imMessage);
}
