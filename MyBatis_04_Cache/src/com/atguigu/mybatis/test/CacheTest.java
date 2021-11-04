package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author CJYong
 * @create 2021-09-01 9:51
 */
public class CacheTest {

    public SqlSessionFactory getsqlSessionFactory() throws IOException {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }

    @Test
    /**
     *一级缓存（本地缓存）测试
     */
    public void test1() throws IOException{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee emp1 = mapper.getEmpById(1);
            Employee emp2 = mapper.getEmpById(1);
            System.out.println(emp1);
            System.out.println(emp2);
            System.out.println(emp1 == emp2);

        }finally {
            openSession.close();
        }
    }

    @Test
    /**
     * 二级缓存（全局缓存）测试
     */
    public void test2() throws IOException{
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession1 = sqlSessionFactory.openSession(true);
        SqlSession openSession2 = sqlSessionFactory.openSession(true);
        try {
            EmployeeMapper mapper1 = openSession1.getMapper(EmployeeMapper.class);
            EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
            Employee emp1 = mapper1.getEmpById(1);
            System.out.println(emp1);
            openSession1.close();

            //第二次查询从二级缓存中拿到数据，并没有发送sql语句
            Employee emp2 = mapper2.getEmpById(1);
            System.out.println(emp2);
            openSession2.close();


        }finally {
            openSession1.close();
            openSession2.close();
        }
    }



}
