package com.xcu.base;

import org.junit.Test;

import java.sql.*;

public class JDBCOperation {

    @Test
    public void testQuerySingleRowAndCol() throws Exception {
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.获取数据库连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", "root", "liuning19881117");

        // 3.预编译SQL语句得到PreparedStatement对象
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as count from t_emp");

        // 4.执行SQL语句得到结果
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5.处理数据
        while (resultSet.next()) {
            int count = resultSet.getInt("count");
            System.out.println(count);
        }

        // 6.释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testQuerySingleRow() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", "root", "liuning19881117");

        PreparedStatement preparedStatement = connection.prepareStatement("select emp_id, emp_name, emp_salary, emp_age from t_emp where emp_age > ?");

        preparedStatement.setInt(1, 25);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            String empSalary = resultSet.getString("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testInsertSingleRow() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", "root", "liuning19881117");

        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_emp(emp_name, emp_salary, emp_age) values(?, ?, ?)");

        preparedStatement.setString(1, "rose");
        preparedStatement.setDouble(2, 499.788);
        preparedStatement.setInt(3, 23);

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            System.out.println("successfully");
        } else {
            System.out.println("fail");
        }

        preparedStatement.close();
        connection.close();

    }



}
