package com.atguigu.mybatis.typehandler;

import com.atguigu.mybatis.bean.EmpStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Parameter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**1.实现typehandler接口或者继承basetypehandler
 * @author CJYong
 * @create 2021-09-05 10:44
 */
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus> {
    @Override
    /**
     * 定义当前数据如何保存到数据库中
     */
    public void setParameter(PreparedStatement preparedStatement, int i, EmpStatus empStatus, JdbcType jdbcType) throws SQLException {
        System.out.println("要保存的状态码为：" + empStatus.getCode());
        preparedStatement.setString(i, empStatus.getCode().toString());

    }

    @Override
    public EmpStatus getResult(ResultSet resultSet, String s) throws SQLException {
        //根据从数据库中拿到的枚举状态码返回一个枚举对象
        int code = resultSet.getInt(s);
        System.out.println("从数据库中获取的状态码为：" + code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public EmpStatus getResult(ResultSet resultSet, int i) throws SQLException {
        //根据从数据库中拿到的枚举状态码返回一个枚举对象
        int code = resultSet.getInt(i);
        System.out.println("从数据库中获取的状态码为：" + code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public EmpStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        //根据从数据库中拿到的枚举状态码返回一个枚举对象
        int code = callableStatement.getInt(i);
        System.out.println("从数据库中获取的状态码为：" + code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }
}
