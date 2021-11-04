package com.atguigu.mybatis.dao;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import javax.swing.plaf.nimbus.State;
import java.util.Properties;

/**
 * @author CJYong
 * @create 2021-09-04 12:56
 */

/**
 * 完成插件的签名：告诉mybatis当前插件用来拦截哪个对象的哪个方法
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class,method = "parameterize",args = java.sql.Statement.class)
        }
)
public class MyFirstPlugin implements Interceptor {
    @Override
    /**
     * 拦截目标方法的执行
     */
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("这是MyFirstPlugin的intercept方法：" + invocation.getMethod());

        //动态改变sql运行的参数： 以前查1号员工，实际查询3号员工信息
        Object target = invocation.getTarget();
        System.out.println("当前拦截到的对象：" + target);
        //拿到参数对象
        MetaObject metaObject = SystemMetaObject.forObject(target);
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句运行的参数是：" + value);
        //修改sql语句的参数
        metaObject.setValue("parameterHandler.parameterObject",11);

        //执行目标方法
        Object proceed = invocation.proceed();
        //返回执行后的执行方法
        return proceed;
    }

    @Override
    /**
     * 包装目标对象，为目标对象创建代理对象
     */
    public Object plugin(Object o) {
        System.out.println("这是MyFirstPlugin的plugin方法:" + o);
        //借助Plugin.wrap来创建代理对象
        Object wrap = Plugin.wrap(o, this);
        //返回包装后的对象
        return wrap;
    }

    @Override
    /**
     * 将插件注册时的property属性设置进来
     */
    public void setProperties(Properties properties) {
        System.out.println("这是MyFirstPlugin的setProperties方法");
        System.out.println("插件的配置信息" + properties);
    }
}
