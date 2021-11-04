package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CJYong
 * @create 2021-08-30 16:10
 */
public interface EmployeeDynamicSQL {

    //携带了哪个字段查询条件就带上这个字段的值
    public List<Employee> getEmpByConditionIf(Employee employee);

    public List<Employee> getEmpByConditionTrim(Employee employee);

    public List<Employee> getEmpByConditionChoose(Employee employee);

    public void updateEmp(Employee employee);

    public List<Employee> getEmpByConditionForeach(@Param("ids") List<Integer> integers);

    public void addEmps(@Param("emps") List<Employee> emps);
}
