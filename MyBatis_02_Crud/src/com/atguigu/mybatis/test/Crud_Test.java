package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Department;
import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.DepartmentMapper;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author CJYong
 * @create 2021-08-24 17:39
 */
public class Crud_Test {

    public SqlSessionFactory getsqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }

    @Test
    /**
     * 根据部门id查询该部门的所有员工
     */
    public void test1() throws Exception{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
//            1.结果集封装
//            Department allDeptById = mapper.getAllDeptById(2);
//            System.out.println(allDeptById);
//            System.out.println("**********************");
//            System.out.println(allDeptById.getEmps());
//            2.分段查询
            Department deptByIdStep = mapper.getDeptByIdStep(2);
            System.out.println(deptByIdStep);
            System.out.println("******************");
            System.out.println(deptByIdStep.getEmps());
        }finally {
            openSession.close();
        }

    }

    @Test
    public void getEmpByIdPlus() throws Exception{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
//            Employee emp = mapper.getEmpById(8);
//            System.out.println(emp);

//            Employee employee = mapper.getEmpAndDept(1);
//            System.out.println(employee);
//            System.out.println(employee.getDept());

            Employee emp = mapper.getEmpByIdStep(8);
            System.out.println(emp);
            System.out.println(emp.getDept());
        }finally {
            openSession.close();
        }
    }



    @Test
    public void getEmpsByLastName() throws IOException{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            //模糊查询返回 list 集合
//            List<Employee> emps = mapper.getEmpsByLastName("%王五%");
//            for (Employee employee : emps){
//                System.out.println(employee);
//            }

            //查询返回Map集合
//            Map<String, Object> empByIdMap = mapper.getEmpByIdMap(1);
//            System.out.println(empByIdMap);

            //以id为键，employee为值输出map
            Map<Integer, Employee> employeeMap = mapper.getEmpByLastNameMap("王五");
            System.out.println(employeeMap);

        }finally {
            openSession.close();
        }
    }

//    mybatis对于查询语句多个参数的测试
    @Test
    public void getEmpByIdAndLastName() throws IOException{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpByIdAndLastName(3, "张三");
            System.out.println(employee);
        }finally {
            openSession.close();
        }
    }



    @Test
    public void testAdd() throws IOException {
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(null, "王五", "zh@qq.com", "0",new Department(3,"测试部"));
            mapper.addEmp(employee);
            System.out.println(employee.getId());
            openSession.commit();
        }finally {
            openSession.close();
        }
    }

    @Test
    public void testDelete() throws IOException {
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            mapper.deleteEmp(6);
            openSession.commit();
        }finally {
            openSession.close();
        }
    }

    @Test
    public void testUpdate() throws IOException {
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(4, "杯子", "gsan@qq.com", "1", new Department(2, "开发部"));
            mapper.updateEmp(employee);
            openSession.commit();
        }finally {
            openSession.close();
        }
    }

    @Test
    public void testQuery() throws IOException {
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(1);
            System.out.println(emp);
            openSession.commit();
        }finally {
            openSession.close();
        }
    }





}
