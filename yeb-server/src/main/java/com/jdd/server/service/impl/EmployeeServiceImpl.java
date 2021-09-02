package com.jdd.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.EmployeeMapper;
import com.jdd.server.pojo.Employee;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.pojo.RespPageBean;
import com.jdd.server.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-08-31
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    //查询所有员工(分页)
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer pageSize, Employee employee, LocalDate[] beginDateScope) {
        // 开启分页
        Page<Employee> page = new Page<>(currentPage,pageSize);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        return new RespPageBean(employeeByPage.getTotal(),employeeByPage.getRecords());
    }

    @Override
    public RespBean getMaxWorkId() {
        // 这个List中只有一个值,Map中只有一条值,就是 "max(workId):0000100"
        // 这条语句的对应SQL语句就是 SELECT MAX(workId) FROM t_employee
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workId)"));
        // workId是char类型 ,注意查询出来的名字不是workId,而是 max(workId)
        Integer workId = Integer.parseInt(maps.get(0).get("max(workId)").toString())+1;
        // 转回String类型
        String newWorkId = String.format("%08d",workId);
        return RespBean.success(null,newWorkId);
    }

    @Override
    public RespBean addEmployee(Employee employee) {
        // 处理合同期限,保留两位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if(1==employeeMapper.insert(employee)){
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }
}
