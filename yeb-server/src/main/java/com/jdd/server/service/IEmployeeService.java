package com.jdd.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.server.pojo.Employee;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-08-31
 */
public interface IEmployeeService extends IService<Employee> {
    //查询所有员工(分页)
    RespPageBean getEmployeeByPage(Integer currentPage, Integer pageSize, Employee employee, LocalDate[] beginDateScope);
    // 获取工号
    RespBean getMaxWorkId();
    // 添加员工
    RespBean addEmployee(Employee employee);
    // 查询员工(即可查询所有,也可以根据ID查询),返回的结果是自定义的数据集(不用自带的查询的理由)
    List<Employee> getEmployee(Integer id);
}
