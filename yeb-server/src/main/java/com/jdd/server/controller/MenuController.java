package com.jdd.server.controller;


import com.jdd.server.pojo.Menu;
import com.jdd.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "通过管理员id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId(){
        // 这里不通过前端传入ID, 而是在后台SpringSecurity上下文中获取用户的ID
        return menuService.getMenusByAdminId();
    }

}
