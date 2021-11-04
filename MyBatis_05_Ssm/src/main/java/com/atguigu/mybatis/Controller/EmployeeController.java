package com.atguigu.mybatis.Controller;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.service.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author CJYong
 * @create 2021-09-01 19:36
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployService employService;

    @RequestMapping("/getemps")
    public ModelAndView emps(){
        ModelAndView mav = new ModelAndView();
        List<Employee> emps = employService.getemps();
        mav.addObject("allEmps",emps);
        mav.setViewName("empList");
        return mav;

    }
}
