package com.atguigu.mybatis.service;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CJYong
 * @create 2021-09-01 19:39
 */
@Service
public class EmployService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public List<Employee> getemps(){
        return employeeMapper.getAllEmps();
    }



}
