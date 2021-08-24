package com.jdd.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 通过管理员id查询菜单列表
     * @return
     */
    List<Menu> getMenuByAdminId();
}
