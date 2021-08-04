package com.jdd.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.DepartmentMapper;
import com.jdd.server.pojo.Department;
import com.jdd.server.service.IDepartmentService;
import org.springframework.stereotype.Service;

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

}
