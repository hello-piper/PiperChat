package piper.im.common.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import piper.im.common.pojo.config.DbConfig;
import piper.im.common.util.YamlUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DbUtil
 *
 * @author piper
 */
public class DbUtil {
    private static final Logger log = LogManager.getLogger(DbUtil.class);
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();
    private static DbConfig config;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            config = YamlUtil.getConfig("db", DbConfig.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (null == connection) {
            connection = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
            CONNECTION_THREAD_LOCAL.set(connection);
        }
        return connection;
    }

    public static void closeConnection() {
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
}
