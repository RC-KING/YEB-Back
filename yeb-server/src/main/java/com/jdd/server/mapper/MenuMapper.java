package com.jdd.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdd.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface MenuMapper extends BaseMapper<Menu> {
    // 根据用户id,查询该用户可访问的菜单
    List<Menu> getMenusByAdminId(Integer id);

    // 查询所有的Menu以及该Menu对应的角色
    List<Menu> getAllMenusWithRole();

    // 查询所有菜单
    List<Menu> getAllMenus();
}
