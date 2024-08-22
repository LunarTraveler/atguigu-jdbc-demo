package com.xcu.advanced;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.xcu.advanced.pojo.Employee;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class JDBCAdvanced {

    @Test
    public void testORM() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "liuning19881117");

        PreparedStatement preparedStatement = connection.prepareStatement("select emp_id, emp_name, emp_salary, emp_age from t_emp where emp_age = ?");

        preparedStatement.setInt(1, 41);

        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            Integer empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            Double empSalary = resultSet.getDouble("emp_salary");
            Integer empAge = resultSet.getInt("emp_age");
            employee = new Employee(empId, empName, empSalary, empAge);
        }

        System.out.println(employee);

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testOPMPK() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "liuning19881117");

        String sql = "insert into t_emp(emp_name, emp_salary, emp_age) values(?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Employee employee = new Employee(null, "jack", 123.45, 18);
        preparedStatement.setString(1, employee.getEmpName());
        preparedStatement.setDouble(2, employee.getEmpSalary());
        preparedStatement.setInt(3, employee.getEmpAge());

        int result = preparedStatement.executeUpdate();

        ResultSet generatedKeys = null;

        if (result > 0) {
            System.out.println("successfully");
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer empId = generatedKeys.getInt(1);
                employee.setEmpId(empId);
            }
            System.out.println(employee);
        } else {
            System.out.println("failed");
        }

        if (generatedKeys != null) {
            generatedKeys.close();
        }
        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testMoreInsert() throws SQLException {

        // 1.注册驱动
//        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.获取数据库连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "liuning19881117");

        // 3.预编译SQL语句
        String sql = "insert into t_emp(emp_name, emp_salary, emp_age) values(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        String[] GENERATE_SOURCE = new String[]{"0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"};
        int STR_LEN = 5, n = GENERATE_SOURCE.length;

        long start = System.currentTimeMillis();

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            StringBuffer empName = new StringBuffer();
            for (int j = 0;j < STR_LEN; j++) {
                empName.append(GENERATE_SOURCE[random.nextInt(n)]);
            }
            double empSalary = random.nextInt(1000000) / 1000.0;
            int empAge = random.nextInt(100);
            preparedStatement.setString(1, empName.toString());
            preparedStatement.setDouble(2, empSalary);
            preparedStatement.setInt(3, empAge);
            preparedStatement.executeUpdate();
        }

        long end = System.currentTimeMillis();

        System.out.println("消耗时间："+(end - start));

        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testBatch() throws SQLException {

        //1.注册驱动
//        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取连接                                              jdbc:mysql:///atguigu?rewriteBatchedStatements=true
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true", "root", "liuning19881117");

        //3.编写SQL语句
        /*
            注意：1、必须在连接数据库的URL后面追加?rewriteBatchedStatements=true，允许批量操作
                2、新增SQL必须用values。且语句最后不要追加;结束
                3、调用addBatch()方法，将SQL语句进行批量添加操作
                4、统一执行批量操作，调用executeBatch()
         */
        String sql = "insert into t_emp (emp_name,emp_salary,emp_age) values (?,?,?)";

        //4.创建预编译的PreparedStatement，传入SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //获取当前行代码执行的时间。毫秒值
        long start = System.currentTimeMillis();

        //执行批量操作
        String[] GENERATE_SOURCE = new String[]{"0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"};
        int STR_LEN = 5, n = GENERATE_SOURCE.length;

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            StringBuffer empName = new StringBuffer();
            for (int j = 0;j < STR_LEN; j++) {
                empName.append(GENERATE_SOURCE[random.nextInt(n)]);
            }
            double empSalary = random.nextInt(1000000) / 1000.0;
            int empAge = random.nextInt(100);
            preparedStatement.setString(1, empName.toString());
            preparedStatement.setDouble(2, empSalary);
            preparedStatement.setInt(3, empAge);

            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

        long end = System.currentTimeMillis();

        System.out.println("消耗时间："+ (end - start));

        preparedStatement.close();
        connection.close();

    }

}
