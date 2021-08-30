package com.jdd.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.server.pojo.Department;
import com.jdd.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface IDepartmentService extends IService<Department> {
    //获取所有部门
    List<Department> getAllDepartments();
    // 添加部门
    RespBean addDepartment(Department department);
    // 删除部门
    RespBean deleteDepartment(Integer id);
}
