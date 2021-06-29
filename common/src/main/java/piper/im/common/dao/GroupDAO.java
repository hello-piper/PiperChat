package piper.im.common.dao;

import piper.im.common.pojo.entity.Group;

public interface GroupDAO {

    Group getById(String id);

    boolean insert(Group group);
}
