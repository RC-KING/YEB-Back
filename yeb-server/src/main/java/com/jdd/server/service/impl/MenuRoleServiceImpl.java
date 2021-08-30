package com.jdd.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.mapper.MenuRoleMapper;
import com.jdd.server.pojo.MenuRole;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;


    @Override
    // 事务的注解,因为该方法中涉及删除和添加操作,保证两者的一致性
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        // 第一步删除rid下的所有菜单
        // 不能直接使用menuRoleMapper.deleteById()方法,因为这个ID是主键id,我们需要的是通过rid删除
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        // 将传入的mids全部添加进入该rid下
        if (null == mids || 0 == mids.length) {
            // 若传入的为空,则不做处理,直接返回成功
            return RespBean.success("删除成功");
        }
        // 通过mapper写select语句进行批量增加
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        if (null == result || result != mids.length) {
            return RespBean.error("更新失败");
        }
        return RespBean.success("更新成功");
    }
}
