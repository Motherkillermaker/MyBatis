package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.bean.EmployeeExample;
import com.atguigu.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
   /**
    * 运行mbg.xml文件
    */
   public void testMBG() throws Exception{
       List<String> warnings = new ArrayList<String>();
       boolean overwrite = true;
       File configFile = new File("mbg.xml");
       ConfigurationParser cp = new ConfigurationParser(warnings);
       Configuration config = cp.parseConfiguration(configFile);
       DefaultShellCallback callback = new DefaultShellCallback(overwrite);
       MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
       myBatisGenerator.generate(null);
   }

   @Test
   public void test1() throws IOException{
       SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
       SqlSession openSession = sqlSessionFactory.openSession(true);
       try {
           EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
           //xxxExample 用来封装查询条件
           EmployeeExample employeeExample = new EmployeeExample();
//           1.查询所有
//           employeeExample.createCriteria().andIdIsNotNull();
//           2.模糊查询
           EmployeeExample.Criteria criteria1 = employeeExample.createCriteria();
           criteria1.andLastNameLike("%王%");
           criteria1.andDeptIdEqualTo(3);
           //添加or条件
           EmployeeExample.Criteria criteria2 =employeeExample.createCriteria();
           criteria2.andEmailLike("%je%");
           employeeExample.or(criteria2);

           List<Employee> employees = mapper.selectByExample(employeeExample);
           for(Employee employee : employees){
               System.out.println(employee.getId());
           }

       }finally {
           openSession.close();
       }
   }


}
