package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author CJYong
 * @create 2021-08-23 15:15
 */
@Component
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public List<Employee> getAllEmps();

}
