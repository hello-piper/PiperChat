package piper.im.repository.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanHandler;
import piper.im.common.dao.GroupDAO;
import piper.im.common.pojo.entity.Group;

import java.sql.SQLException;

/**
 * GroupDAOImpl
 *
 * @author piper
 */
public class GroupDAOImpl implements GroupDAO {

    @Override
    public Group getById(String id) {
        try {
            return Db.use().find(Entity.create("group").set("id", id), new BeanHandler<>(Group.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Group group) {
        int count = 0;
        try {
            count = Db.use().insert(Entity.parseWithUnderlineCase(group));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}
