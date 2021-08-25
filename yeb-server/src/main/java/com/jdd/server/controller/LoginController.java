package com.jdd.server.controller;

import com.jdd.server.pojo.Admin;
import com.jdd.server.pojo.AdminLoginParam;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回Token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    /**
     * 参数为Principal类,这个对象是SpringSecurity上下文中的对象,之前我们对这个对象刷新过
     * @param principal
     * @return
     */
    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (null==principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        // 对个人隐私的保护
        admin.setPassword(null);
        // 将该用户的权限设置到roles列表中
        admin.setRoles(adminService.getRoles(admin.getId()));

        return admin;
    }

    /**
     * 直接返回成功,让前端获取到200的信息,然后让前端自己去将请求头中的Token删除
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("退出登录成功");
    }





}
