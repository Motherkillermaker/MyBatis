package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author CJYong
 * @create 2021-08-23 13:55
 */
public class MyBatisTest {

    /**
     *1.根据xml配置文件（全局配置文件）创建一个sqlSessionFactory对象
     *      包含数据源和一些运行环境信息
     *2.sql映射文件：配置了每一个sql以及sql的封装规则等（EmployeeMapper.xml）
     *3.将sql映射文件注册在全局配置文件中（mybatis-config.xml）
     *4.编写代码
     *      1)根据全局配置文件得到sqlSessionFactory
     *      2)获取sqlSession实例进行增删改查（一个sqlSession代表和数据库的一次会话，用完即关）
     *      3)使用sql的唯一表示告诉MyBatis执行哪个sql，sql均保存在sql映射文件中
     *
     * @throws IOException
     */

    /**
     * 根据全局配置文件得到sqlSessionFactory
     * @return
     * @throws IOException
     */
    public SqlSessionFactory getsqlSessionFactory() throws IOException{
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }

    @Test
    public void test1() throws IOException{

        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();

        /**
         * 2.获取sqlSession实例（可以直接执行已经映射的sql语句）
         * sql唯一标识：             select标签的Id值
         * 执行sql所需要的参数：
         */
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Employee employee = openSession.selectOne("com.atguigu.mybatis.dao.EmployeeMapper.getEmpById", 1);

            System.out.println(employee);
        }finally {
            openSession.close();
        }
    }

    /**
     * sql与接口绑定后的查询
     * 1.接口式编程
     *      原生：     DaO         -- >  DaoImpl
     *   mybatis:     Mapper      -- >   xxMapper.xml(包含sql查询语句)
     * 2. SqlSession代表一次会话，使用完必须关闭（底层与connection一样，非线程安全），每次使用都应去获取新的对象
     * 3. mapper接口没有实现类，但 mybatis 会为该接口创建一个代理对象（将接口和xml进行绑定）来执行增删改查
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        //1.获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        //2.获取SqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        //3.获取接口实现类对象
        //会为接口自动创建一个代理对象，使用代理对象去执行增删改查
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Employee emp = mapper.getEmpById(1);

            System.out.println(mapper.getClass());
            System.out.println(emp);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test3() throws IOException {
        SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(employee);
        }finally {
            openSession.close();
        }
    }


}
