/*
 * Copyright 2020 The PiperChat
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.server.web.repository.impl;

import io.piper.common.db.DbUtil;
import io.piper.common.pojo.entity.ImUser;
import io.piper.server.web.repository.dao.UserDAO;

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
    public ImUser getById(String id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ImUser imUser = null;
            //获取连接
            Connection conn = DbUtil.getConnection();
            //sql
            String sql = "select * from user where id = ?";
            //预编译SQL
            stmt = conn.prepareStatement(sql);
            //传参
            stmt.setString(1, id);
            //执行
            rs = stmt.executeQuery();
            while (rs.next()) {
                imUser = new ImUser();
                imUser.setId(rs.getLong("id"));
                imUser.setAge(rs.getInt("age"));
                imUser.setEmail(rs.getString("email"));
                imUser.setNickname(rs.getString("nickname"));
                imUser.setAvatar(rs.getString("avatar"));
                imUser.setPhone(rs.getString("phone"));
                imUser.setGender(rs.getInt("gender"));
                imUser.setPassword(rs.getString("password"));
                imUser.setSalt(rs.getString("salt"));
                imUser.setStatus(rs.getInt("status"));
                imUser.setCreateUser(rs.getString("create_user"));
                imUser.setCreateTime(rs.getLong("create_time"));
            }
            return imUser;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.releaseConnection();
            DbUtil.release(rs, stmt);
        }
        return null;
    }

    @Override
    public boolean insert(ImUser imUser) {
        PreparedStatement stmt = null;
        try {
            //获取连接
            Connection conn = DbUtil.getConnection();
            //sql
            String sql = "INSERT INTO user(create_time,age,email,nickname,avatar,phone,gender,password,salt,status,create_user)"
                    + "values(?,?,?,?,?,?,?,?,?,?,?)";
            //预编译
            stmt = conn.prepareStatement(sql);
            //传参
            stmt.setLong(1, imUser.getCreateTime());
            stmt.setInt(2, imUser.getAge());
            stmt.setString(3, imUser.getEmail());
            stmt.setString(4, imUser.getNickname());
            stmt.setString(5, imUser.getAvatar());
            stmt.setString(6, imUser.getPhone());
            stmt.setInt(7, imUser.getGender());
            stmt.setString(8, imUser.getPassword());
            stmt.setString(9, imUser.getSalt());
            stmt.setInt(10, imUser.getStatus());
            stmt.setString(11, imUser.getCreateUser());
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
