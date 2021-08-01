/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
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
package io.piper.common.db;

import io.piper.common.pojo.config.DbProperties;
import io.piper.common.util.StringUtil;
import io.piper.common.util.YamlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * DbUtil
 *
 * @author piper
 */
public class DbUtil {
    private static final Logger log = LogManager.getLogger(DbUtil.class);
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();
    private static DbProperties config;

    static {
        try {
            config = YamlUtil.getConfig("db", DbProperties.class);
            if (StringUtil.isAnyEmpty(config, config.getDriver(), config.getUrl(), config.getUsername(), config.getPassword())) {
                log.error("init DB error, has empty config.");
            } else {
                Class.forName(config.getDriver());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (null == connection) {
            connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
            CONNECTION_THREAD_LOCAL.set(connection);
        }
        return connection;
    }

    public static void releaseConnection() {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            CONNECTION_THREAD_LOCAL.remove();
        }
    }

    public static void release(Statement stmt) {
        release(null, stmt);
    }

    public static void release(ResultSet rs, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
