package com.xcu.senior;

import com.xcu.senior.dao.EmployeeDao;
import com.xcu.senior.dao.impl.EmployeeDaoImpl;
import com.xcu.senior.pojo.Employee;
import com.xcu.senior.util.JDBCUtil;
import com.xcu.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class JDBCToolsTest {

    @Test
    public void test() {

        Connection connection = JDBCUtil.getConnection();

        System.out.println(connection);

        JDBCUtil.release(connection);

    }

    @Test
    public void testV2() {
//        Connection connection1 = JDBCUtil.getConnection();
//        Connection connection2 = JDBCUtil.getConnection();
//
//        System.out.println(connection1);
//        System.out.println(connection2);

        Connection connection1 = JDBCUtilV2.getConnection();
        Connection connection2 = JDBCUtilV2.getConnection();

        System.out.println(connection1);
        System.out.println(connection2);

    }

    @Test
    public void test3() {
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        List<Employee> employees = employeeDao.selectAll();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void test4() {
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        Employee employee = employeeDao.selectByEmpId(41);
        System.out.println(employee);
    }

    @Test
    public void test5() {
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        Employee employee = new Employee(7, null, 999.99, null);
        int res = employeeDao.update(employee);
        System.out.println(res);
    }
    

}
