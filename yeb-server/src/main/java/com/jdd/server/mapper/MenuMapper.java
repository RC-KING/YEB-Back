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

    List<Menu> getMenusByAdminId(Integer id);

    List<Menu> getAllMenusWithRole();
}
