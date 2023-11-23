package com.czx.service.impl;

import com.czx.pojo.AuthToken;
import com.czx.pojo.Menu;
import com.czx.pojo.Permission;
import com.czx.service.PermissionService;
import com.czx.service.UserService;
import com.czx.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class AuthTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    PermissionService permissionService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        // 检查注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AuthToken authTokenClass = handlerMethod.getBeanType().getAnnotation(AuthToken.class);
            AuthToken authTokenMethod = handlerMethod.getMethod().getAnnotation(AuthToken.class);

            // 如果方法或类上标记了注解
            if (authTokenMethod != null || authTokenClass != null) {
                // 从请求中获取Access Token
                String accessToken = request.getHeader("Authorization");
                accessToken = accessToken.substring(7);

                System.out.println("begin");
                System.out.println(accessToken);
                System.out.println("end");

                // 验证Access Token
                if (accessToken != null && redisTemplate.hasKey(accessToken)) {
                    System.out.println("测试成功！！！！！");
                    System.out.println((String) redisTemplate.opsForValue().get(accessToken));
                    System.out.println("测试成功！！！！！");
                    // 还要做一些权限认证什么的
                    String username = jwtTokenUtil.getUsernameFromToken(accessToken);
                    System.out.println("username = " + username);
                    String url = request.getRequestURI();
                    System.out.println("url = " + url);
                    List<Permission> permissions = permissionService.getByUsername(username);
                    System.out.println("begin");
                    for (Permission permission : permissions) {
                        System.out.println(permission.getPermission_id() + " " + permission.getPermission_name() + " " + permission.getDescription() + " " + permission.getUrl());
                    }
                    System.out.println("end");
                    for (Permission permission : permissions) {
                        if (permission.getUrl().equals(url)) {
                            System.out.println("权限验证成功");
                            logger.info("权限验证成功");
                            return true;
                        }
                    }
                    System.out.println("权限验证失败");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
            }
        }
        return true;
    }
}