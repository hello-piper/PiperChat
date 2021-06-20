package piper.im.repository.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanHandler;
import piper.im.common.dao.GroupDao;
import piper.im.common.pojo.entity.Group;

import java.sql.SQLException;

public class GroupDaoImpl implements GroupDao {

    @Override
    public Group getById(String id) throws SQLException {
        return Db.use().find(Entity.create("group").set("id", id), new BeanHandler<>(Group.class));
    }

    @Override
    public boolean insert(Group group) throws SQLException {
        int count = Db.use().insert(Entity.parseWithUnderlineCase(group));
        return count > 0;
    }
}
