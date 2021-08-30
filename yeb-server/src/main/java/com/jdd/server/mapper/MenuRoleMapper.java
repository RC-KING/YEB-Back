package com.jdd.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdd.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    // 更新角色菜单
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
