package com.xcu.senior.dao;

import com.xcu.senior.pojo.Employee;

import java.util.List;

/**
 * EmployeeDao这个类对应的是t_emp这张表的增删改查的操作
 */
public interface EmployeeDao {
    /**
     * 数据库对应的查询所有的操作
     * @return 表中所有的数据
     */
    List<Employee> selectAll();

    /**
     * 数据库对应的根据empId查询单个员工数据操作
     * @param empId 主键列
     * @return 一个员工对象（一行数据）
     */
    Employee selectByEmpId(Integer empId);

    /**
     * 数据库对应的新增一条员工数据
     * @param employee ORM思想中的一个员工对象
     * @return 受影响的行数
     */
    int insert(Employee employee);

    /**
     * 数据库对应的修改一条员工数据
     * @param employee ORM思想中的一个员工对象
     * @return 受影响的行数
     */
    int update(Employee employee);

    /**
     * 数据库对应的根据empId删除一条员工数据
     * @param empId 主键列
     * @return 受影响的行数
     */
    int delete(Integer empId);
}
