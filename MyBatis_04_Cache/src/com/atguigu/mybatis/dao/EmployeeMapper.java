package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;

/**
 * @author CJYong
 * @create 2021-08-23 15:15
 */
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

}
