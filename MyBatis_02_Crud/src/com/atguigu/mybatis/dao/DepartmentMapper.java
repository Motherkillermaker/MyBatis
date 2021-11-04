package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Department;

/**
 * @author CJYong
 * @create 2021-08-27 10:03
 */
public interface DepartmentMapper {

    public Department getDeptById(Integer id);

    public Department getAllDeptById(Integer id);

    public Department getDeptByIdStep(Integer id);
}
