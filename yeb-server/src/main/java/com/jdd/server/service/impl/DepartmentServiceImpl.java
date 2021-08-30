package com.jdd.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.DepartmentMapper;
import com.jdd.server.pojo.Department;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    //获取所有部门
    public List<Department> getAllDepartments() {
        // 这里使用MyBatis递归查询, 传入的是根id
        return departmentMapper.getAllDepartments(-1);
    }

    @Override
    // 添加部门
    public RespBean addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        if (1==department.getResult()){
            return RespBean.success("添加成功!",department);
        }
        return RespBean.error("添加失败!");
    }

    @Override
    // 删除部门
    public RespBean deleteDepartment(Integer id) {
        // 新建一个department对象用于接收result
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        if (-2==department.getResult()){
            return RespBean.error("该部门下存在子部门,删除失败!");
        }
        if (-1==department.getResult()){
            return RespBean.error("该部门下存在员工,删除失败!");
        }
        if (1==department.getResult()){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }


}
