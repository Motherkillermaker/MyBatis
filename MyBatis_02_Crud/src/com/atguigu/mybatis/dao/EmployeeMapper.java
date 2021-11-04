package com.atguigu.mybatis.dao;


import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author CJYong
 * @create 2021-08-23 15:15
 */
public interface EmployeeMapper {

    //告诉mybatis封装Map的时候以id属性作为键
    @MapKey("id")
    public Map<Integer, Employee> getEmpByLastNameMap(String lastname);

    public Map<String,Object> getEmpByIdMap(Integer id);

    public List<Employee> getEmpsByLastName(String lastname);

    public Employee getEmpByIdAndLastName(@Param("id")Integer id, @Param("lastName") String lastname);

    public Employee getEmpById(Integer id);

    public void addEmp(Employee employee);

    public void deleteEmp(Integer id);

    public void updateEmp(Employee employee);

    public List<Employee> getEmpByDeptId(Integer id);


}
