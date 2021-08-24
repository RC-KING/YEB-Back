package com.jdd.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.MenuMapper;
import com.jdd.server.pojo.Admin;
import com.jdd.server.pojo.Menu;
import com.jdd.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    // 数据库操作
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过管理员id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        // 通过SpringSecurity上下文获取管理员的ID
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        // 先通过Redis缓存去取该管理员对应权限的操作菜单
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        List<Menu> menuList = (List<Menu>)stringObjectValueOperations.get("menu_" + adminId);
        // 若没有获取到,则转为去数据库获取
        if (CollectionUtils.isEmpty(menuList)){
            menuList = menuMapper.getMenusByAdminId(adminId);
            // 将数据存入缓存
            stringObjectValueOperations.set("menu_" + adminId,menuList);
        }
       return menuList;
    }
}
