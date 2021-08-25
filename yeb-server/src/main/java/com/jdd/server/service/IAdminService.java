package com.jdd.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdd.server.pojo.Admin;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
public interface IAdminService extends IService<Admin> {


    Admin getAdminByUserName(String username);

    RespBean login(String username, String password, String code, HttpServletRequest request);

    // 根据用户id,查询拥有的角色
    List<Role> getRoles(Integer adminId);


}
