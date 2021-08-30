package com.jdd.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdd.server.pojo.Menu;
import com.jdd.server.pojo.MenuRole;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.pojo.Role;
import com.jdd.server.service.IMenuRoleService;
import com.jdd.server.service.IMenuService;
import com.jdd.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {
    @Autowired
    private IRoleService roleService;



    // ========================= 功能1:角色管理操作 ==========================//
    @ApiOperation(value = "获取所有角色信息")
    @GetMapping("/")
    public List<Role> getAllRole(){
        // 这个list是mybatis plus自带的
        return roleService.list();
    }

    @ApiOperation(value = "增加角色")
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        // 角色命名预处理,可被SpringSecurity识别角色
        if (!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if (roleService.save(role)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "删除角色信息")
    @DeleteMapping ("/role/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid){
        if (roleService.removeById(rid)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }


    // ============================= 功能2:菜单操作 ===========================//
    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;

    /**
     * 会将整个菜单的层级目录查询出来(三级菜单,每一层的所有内容)
     */
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    /**
     * 根据角色id查询菜单id,本质上查询的是rid这张表
     * @param rid
     * @return
     */
    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid){
        return menuRoleService.list(new QueryWrapper<MenuRole>()
                // eq("xxx",123) 等同于 SQL语句: Where xxx = 123
                // 查询出来之后是个 MenuRole对象列表 类型
                .eq("rid",rid))
                // 先获取菜单id, 然后转成List类型, 最后接收到的是菜单id
                .stream().map(MenuRole::getMid).collect(Collectors.toList());
    }


    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        return menuRoleService.updateMenuRole(rid,mids);
    }





}
