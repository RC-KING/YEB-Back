package com.jdd.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdd.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface RoleMapper extends BaseMapper<Role> {
    // 根据用户id,查询拥有的角色
    List<Role> getRoles(Integer adminId);
}
