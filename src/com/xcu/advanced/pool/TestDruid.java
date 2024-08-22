package com.xcu.advanced.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.xcu.advanced.JDBCAdvanced;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class TestDruid {

    @Test
    public void testHardCodeDruid() throws SQLException {
        /*
            硬编码：将连接池的配置信息和Java代码耦合在一起。
            1、创建DruidDataSource连接池对象。
            2、设置连接池的配置信息【必须 | 非必须】
            3、通过连接池获取连接对象
            4、回收连接【不是释放连接，而是将连接归还给连接池，给其他线程进行复用】
         */

        //1.创建DruidDataSource连接池对象。
        DruidDataSource druidDataSource = new DruidDataSource();

        //2.设置连接池的配置信息【必须 | 非必须】
        //2.1 必须设置的配置
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql:///atguigu");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("liuning19881117");

        //2.2 非必须设置的配置
        druidDataSource.setInitialSize(10);
        druidDataSource.setMaxActive(20);

        //3.通过连接池获取连接对象
        Connection connection = druidDataSource.getConnection();
        System.out.println(connection);

        //基于connection进行CRUD

        //4.回收连接
        connection.close();
    }

    @Test
    public void testResourcesDruid() throws Exception {

        //1.创建Properties集合，用于存储外部配置文件的key和value值。
        Properties properties = new Properties();

        //2.读取外部配置文件，获取输入流，加载到Properties集合里。
        InputStream inputStream = TestDruid.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream);

        //3.基于Properties集合构建DruidDataSource连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        //4.通过连接池获取连接对象
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        //5.开发CRUD

        //6.回收连接
        connection.close();

    }

    @Test
    public void testSoftCodeDruid() throws Exception {

        Properties properties = new Properties();

        InputStream inputStream = TestDruid.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream);

        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        Connection connection = dataSource.getConnection();

        System.out.println(connection);

        connection.close();

    }

}
