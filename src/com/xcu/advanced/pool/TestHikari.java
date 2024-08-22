package com.xcu.advanced.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class TestHikari {

    @Test
    public void testHardCodeHikari() throws Exception{
        /*
         硬编码：将连接池的配置信息和Java代码耦合在一起。
         1、创建HikariDataSource连接池对象
         2、设置连接池的配置信息【必须 ｜ 非必须】
         3、通过连接池获取连接对象
         4、回收连接
         */
        //1.创建HikariDataSource连接池对象
        HikariDataSource hikariDataSource = new HikariDataSource();

        //2.设置连接池的配置信息【必须 ｜ 非必须】
        //2.1必须设置的配置
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql:///atguigu");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("liuning19881117");

        //2.2 非必须设置的配置
        hikariDataSource.setMinimumIdle(10);
        hikariDataSource.setMaximumPoolSize(20);

        // 3.获取connection对象
        Connection connection = hikariDataSource.getConnection();
        System.out.println(connection);

        connection.close();

    }

    @Test
    public void testSoftCodeHikari() throws Exception{

        Properties properties = new Properties();

        InputStream resourceAsStream = TestHikari.class.getClassLoader().getResourceAsStream("hikari.properties");

        properties.load(resourceAsStream);

        HikariConfig hikariConfig = new HikariConfig(properties);

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        Connection connection = hikariDataSource.getConnection();

        System.out.println(connection);

        connection.close();

    }

}
