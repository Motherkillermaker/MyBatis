package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.bean.EmployeeExample;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.Optional;

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
   /**
    * 测试分页查询
    */
   public void test1() throws IOException{
       SqlSessionFactory sqlSessionFactory = getsqlSessionFactory();
       SqlSession openSession = sqlSessionFactory.openSession();
       try {
           EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
           Page<Object> page = PageHelper.startPage(5, 1);

           EmployeeExample employeeExample = new EmployeeExample();
           EmployeeExample.Criteria criteria = employeeExample.createCriteria();
           criteria.andIdIsNotNull();
           List<Employee> employees = mapper.selectByExample(employeeExample);
           //获取分页的所有信息
           //navigatePages 参数表示要连续显示几页
           PageInfo<Employee> info = new PageInfo<>(employees, 5);

           for(Employee employee : employees){
               System.out.println(employee);
           }
//           System.out.println("当前页码" + page.getPageNum());
//           System.out.println("总记录数：" + page.getTotal());
//           System.out.println("每页记录数：" + page.getPageSize());
//           System.out.println("总页码：" + page.getPages());

           System.out.println("当前页码" + info.getPageNum());
           System.out.println("总记录数：" + info.getTotal());
           System.out.println("每页记录数：" + info.getPageSize());
           System.out.println("总页码：" + info.getPages());
           System.out.println("是否第一页：" + info.isIsFirstPage());

           int[] nums = info.getNavigatepageNums();
           System.out.println("连续显示的页码：");
           for (int i = 0; i < nums.length; i++) {
               System.out.println(nums[i]);
           }

       }finally {
           openSession.close();
       }
   }

   @Test
   /**
    * 测试 MBG 生成器
    */
   public void test2() throws IOException{
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

    /**
     * 插件原理
     * 在四大对象创建的时候
     * 1、每个创建出来的对象不是直接返回的，而是
     * 		interceptorChain.pluginAll(parameterHandler);
     * 2、获取到所有的Interceptor（拦截器）（插件需要实现的接口）；
     * 		调用interceptor.plugin(target);返回target包装后的对象
     * 3、插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP（面向切面）
     * 		我们的插件可以为四大对象创建出代理对象；
     * 		代理对象就可以拦截到四大对象的每一个执行；
     *
     public Object pluginAll(Object target) {
     for (Interceptor interceptor : interceptors) {
     target = interceptor.plugin(target);
     }
     return target;
     }

     */
    /**
     * 插件编写：
     * 1、编写Interceptor的实现类
     * 2、使用@Intercepts注解完成插件签名
     * 3、将写好的插件注册到全局配置文件中
     *
     */
    @Test
    public void testPlugin(){


    }



}
