package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Department;
import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author CJYong
 * @create 2021-08-30 16:33
 */
public class DynamicSQLTest {
    public SqlSessionFactory getsqlSessionFactory() throws IOException {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }

    @Test
    public void test1() throws IOException{
        /**
         *sql 拼装查询
         */
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            EmployeeDynamicSQL mapper = openSession.getMapper(EmployeeDynamicSQL.class);
            Employee employee = new Employee(1,"admin",null,null,null);
//            List<Employee> emps = mapper.getEmpByConditionIf(employee);
//            for(Employee emp : emps){
//                System.out.println(emp);
//            }
            //查询时某些条件未带，SQL拼装可能会出现问题
//            1.给where后面加上 1 = 1，之后的条件都and xxx。
//            2.mybatis可以使用where标签来将所有的查询条件包括在内, mybatis就会将where标签中拼装sql多出来的and或者or去掉

            //测试set标签
            mapper.updateEmp(employee);
        }finally {
            openSession.close();
        }
    }

    @Test
    /**
     * choose标签的使用
     */
    public void test2() throws  IOException{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            EmployeeDynamicSQL mapper = openSession.getMapper(EmployeeDynamicSQL.class);
            Employee employee = new Employee(null,"%王%","zh@qq.com",null,null);
            List<Employee> employees = mapper.getEmpByConditionChoose(employee);
            for(Employee emp : employees){
                System.out.println(emp);
            }
        }finally {
            openSession.close();
        }
    }

    @Test
    /**
     * foreach标签的使用,传入Id值，查询对应的员工信息
     */
    public void test3() throws  IOException{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            EmployeeDynamicSQL mapper = openSession.getMapper(EmployeeDynamicSQL.class);
            List<Employee> list = mapper.getEmpByConditionForeach(Arrays.asList(9, 4, 5));
            for(Employee emp : list){
                System.out.println(emp);
            }
        }finally {
            openSession.close();
        }
    }

    /**
     * 使用foreach标签进行批量插入
     * @throws IOException
     */
    @Test
    public void test4() throws IOException{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            EmployeeDynamicSQL mapper = openSession.getMapper(EmployeeDynamicSQL.class);
            List<Employee> emps = new ArrayList<>();
            emps.add(new Employee(null,"张三丰","zhangsanfeng@qq.com","1",new Department(1,"商务部")));
            emps.add(new Employee(null,"smith","smith@qq.com","1",new Department(3,"财务部")));
            emps.add(new Employee(null,"刘翔","liuxiang@qq.com","1",new Department(2,"研发部")));
            mapper.addEmps(emps);
        }finally {
            openSession.close();
        }
    }




}
