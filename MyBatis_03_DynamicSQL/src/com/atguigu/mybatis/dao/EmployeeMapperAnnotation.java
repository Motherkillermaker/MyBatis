package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * @author CJYong
 * @create 2021-08-24 10:49
 */
public interface EmployeeMapperAnnotation {

    @Select("select id,last_name lastName,email,gender from tbl_employee where id = #{id}")
    public Employee getEmpById(Integer id);

}
