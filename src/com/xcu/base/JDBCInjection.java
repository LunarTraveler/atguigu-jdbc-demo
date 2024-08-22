package com.xcu.base;

import java.sql.*;
import java.util.Scanner;

public class JDBCInjection {

    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.获取数据库连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", "root", "liuning19881117");

        // 3.创建Statement对象(编写SQL语句在里面)
        String sql = "SELECT * FROM t_emp where emp_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        System.out.println("输入查询的名字");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        // 4.执行sql语句获得结果
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5.处理得到的表(元组的集合)
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            String empSalary = resultSet.getString("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        // 6.释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

}
