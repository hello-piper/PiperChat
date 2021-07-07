package piper.im.common.db;

import cn.hutool.core.util.ObjectUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import piper.im.common.pojo.config.DbProperties;
import piper.im.common.util.YamlUtil;

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
            if (ObjectUtil.hasEmpty(config, config.getDriver(), config.getUrl(), config.getUsername(), config.getPassword())) {
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