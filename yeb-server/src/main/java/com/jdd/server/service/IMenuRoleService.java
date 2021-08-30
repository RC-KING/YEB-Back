package com.jdd.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.server.pojo.MenuRole;
import com.jdd.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface IMenuRoleService extends IService<MenuRole> {

    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
