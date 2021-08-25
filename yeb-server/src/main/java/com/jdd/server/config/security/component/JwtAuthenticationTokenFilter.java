package com.jdd.server.config.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt登录授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(tokenHeader);
        // 若authHeader不为空, 或者不是以tokenHead(Bearer)开头的
        if (null!=authHeader && authHeader.startsWith(tokenHead)){
            // 获取Token
            // 对字符串进行截取
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUserNameFormToken(authToken);
            // 在Token中能拿到用户名,但是在SpringSecurity上下文中不能拿到权限,说明用户没有登录
            if (null!=username && null== SecurityContextHolder.getContext().getAuthentication()){
                // 登录了
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 判断Token是否有效
                if(jwtTokenUtil.validateToken(authToken,userDetails)){
                    // 下面的整体功能 就是将Token重新设置回SpringSecurity的上下文
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
