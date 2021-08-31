package com.jdd.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdd.server.pojo.AdminRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    // 更新操作员对应的角色
    Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);

}
