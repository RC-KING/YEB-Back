package com.jdd.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdd.server.config.security.JwtTokenUtil;
import com.jdd.server.mapper.AdminMapper;
import com.jdd.server.pojo.Admin;
import com.jdd.server.pojo.RespBean;
import com.jdd.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jdd
 * @since 2021-08-04
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    // 数据库操作
    @Autowired
    private AdminMapper adminMapper;
    // SpringSecurity的UserDetailsService类
    @Autowired
    private UserDetailsService userDetailsService;
    // 用于密码解析
    @Autowired
    private PasswordEncoder passwordEncoder;
    // JWT工具类
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    // JWT 负载中拿到开头
    @Value("${jwt.tokenHead}")
    private String tokenHead;



    @Override
    public RespBean login(String username, String password,String code, HttpServletRequest request) {
        String captcha =  (String)request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(captcha)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码错误,请重新输入!");
        }

        // 通过用户名去获取UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 若用户名为空 或者 密码不正确, 则让公共返回体返回一个错误
        if (null== userDetails || passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        // 若账户被禁用
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用,请联系管理员");
        }

        // 更新Security登录用户对象(SpringSecurity特性)
        // 参数1:UserDetails  参数2:密码  参数3:权限列表
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);



        // 若都正确,则生成Token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        // 将头部信息存入
        tokenMap.put("tokenHead", tokenHead);
        // 把Token返回给前端
        return RespBean.success("登录成功", tokenMap);
    }



    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(
                new QueryWrapper<Admin>()
                        .eq("username", username)
                        .eq("enabled", true)
        );
    }
}
