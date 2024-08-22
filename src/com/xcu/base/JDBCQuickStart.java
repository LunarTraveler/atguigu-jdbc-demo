package com.xcu.base;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCQuickStart {

    public static void main(String[] args) throws Exception {
        // 1.注册驱动(类加载的方式)
        Class.forName("com.mysql.cj.jdbc.Driver");
        //DriverManager.registerDriver(new Driver());

        // 2.获取数据库连接
        String url = "jdbc:mysql://localhost:3306/atguigu";
        String user = "root";
        String password = "liuning19881117";
        Connection connection = DriverManager.getConnection(url, user, password);

        // 3.创建Statement对象(编写SQL语句在里面)
        String sql = "select * from t_emp";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 4.执行sql语句获得结果
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5.处理得到的表(元组的集合)
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            String empSalary = resultSet.getString("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        // 6.释放资源(先开后关的原则)
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

}