package com.jdd.server.controller;


import com.jdd.server.pojo.Admin;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.pojo.Role;
import com.jdd.server.service.IAdminService;
import com.jdd.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  操作员管理
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keywords){
        return  adminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping("/")
    public RespBean updateAdmin(@RequestBody Admin admin){
        if (adminService.updateById(admin)){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!") ;
    }


    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id){
        if (adminService.removeById(id)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!") ;
    }


    // ================ 角色处理 =================== //
    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.list();
    }


    @ApiOperation(value = "更新操作员的角色")
    @PutMapping("/role")
    public RespBean updateRole(Integer adminId,Integer[] rids){
        return adminService.updateAdminRole(adminId,rids);
    }



}
