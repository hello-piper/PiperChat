package piper.im.repository.impl;

import piper.im.common.dao.UserDAO;
import piper.im.common.db.DbUtil;
import piper.im.common.pojo.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * UserDAOJdbc
 *
 * @author piper
 */
public class UserDAOJdbc implements UserDAO {

    @Override
    public User getById(String id) {
        try {
            User user = null;
            //获取连接
            Connection conn = DbUtil.getConnection();
            //sql
            String sql = "select * from  user where id = ?";
            //预编译SQL
            PreparedStatement ptmt = conn.prepareStatement(sql);
            //传参
            ptmt.setString(1, id);
            //执行
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setAge(rs.getInt("age"));
                user.setEmail(rs.getString("email"));
                user.setNickname(rs.getString("nickname"));
                user.setAvatar(rs.getString("avatar"));
                user.setPhone(rs.getString("phone"));
                user.setGender(rs.getInt("gender"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
                user.setStatus(rs.getInt("status"));
                user.setCreateUser(rs.getString("create_user"));
                user.setCreateTime(rs.getLong("create_time"));
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeConnection();
        }
        return null;
    }

    @Override
    public boolean insert(User user) {
        try {
            //获取连接
            Connection conn = DbUtil.getConnection();
            //sql
            String sql = "INSERT INTO user(id,age,email,nickname,avatar,phone,gender,password,salt,status,create_user,create_time)"
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?)";
            //预编译
            PreparedStatement tempt = conn.prepareStatement(sql);
            //传参
            tempt.setString(1, user.getId());
            tempt.setInt(2, user.getAge());
            tempt.setString(3, user.getEmail());
            tempt.setString(4, user.getNickname());
            tempt.setString(5, user.getAvatar());
            tempt.setString(6, user.getPhone());
            tempt.setInt(7, user.getGender());
            tempt.setString(8, user.getPassword());
            tempt.setString(9, user.getSalt());
            tempt.setInt(10, user.getStatus());
            tempt.setString(11, user.getCreateUser());
            tempt.setLong(12, user.getCreateTime());
            //执行
            return tempt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.closeConnection();
        }
        return false;
    }
}
