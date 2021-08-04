package com.jdd.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.RoleMapper;
import com.jdd.server.pojo.Role;
import com.jdd.server.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
