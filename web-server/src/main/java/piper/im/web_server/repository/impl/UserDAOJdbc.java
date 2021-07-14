package piper.im.web_server.repository.impl;

import piper.im.common.db.DbUtil;
import piper.im.common.pojo.entity.User;
import piper.im.web_server.repository.dao.UserDAO;

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
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            User user = null;
            //获取连接
            Connection conn = DbUtil.getConnection();
            //sql
            String sql = "select * from  user where id = ?";
            //预编译SQL
            stmt = conn.prepareStatement(sql);
            //传参
            stmt.setString(1, id);
            //执行
            rs = stmt.executeQuery();
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
            DbUtil.releaseConnection();
            DbUtil.release(rs, stmt);
        }
        return null;
    }

    @Override
    public boolean insert(User user) {
        PreparedStatement stmt = null;
        try {
            //获取连接
            Connection conn = DbUtil.getConnection();
            //sql
            String sql = "INSERT INTO user(id,age,email,nickname,avatar,phone,gender,password,salt,status,create_user,create_time)"
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?)";
            //预编译
            stmt = conn.prepareStatement(sql);
            //传参
            stmt.setString(1, user.getId());
            stmt.setInt(2, user.getAge());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getNickname());
            stmt.setString(5, user.getAvatar());
            stmt.setString(6, user.getPhone());
            stmt.setInt(7, user.getGender());
            stmt.setString(8, user.getPassword());
            stmt.setString(9, user.getSalt());
            stmt.setInt(10, user.getStatus());
            stmt.setString(11, user.getCreateUser());
            stmt.setLong(12, user.getCreateTime());
            //执行
            return stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.releaseConnection();
            DbUtil.release(stmt);
        }
        return false;
    }
}
