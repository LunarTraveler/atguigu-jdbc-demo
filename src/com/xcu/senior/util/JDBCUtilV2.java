package com.xcu.senior.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
/**
 *    JDBC工具类（V2.0）：
 *        1、维护一个连接池对象。
 *        2、对外提供在连接池中获取连接的方法
 *        3、对外提供回收连接的方法
 *    注意：工具类仅对外提供共性的功能代码，所以方法均为静态方法！
 */
public class JDBCUtilV2 {

    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {

        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection() {
        try {
            Connection connection = threadLocal.get();
            if (connection == null) {
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void release() {
        try {
            Connection connection = threadLocal.get();
            if (connection != null) {
                threadLocal.remove();
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}