package com.swan.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截器");
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("token=")) {
            String token = header.substring(6);
            try {
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    if (Objects.equals("admin", claims.get("roles"))) {       //如果是管理员
                        request.setAttribute("admin_claims", claims);
                    }
                    else if (Objects.equals("user", claims.get("role"))) {
                        request.setAttribute("user_claims", claims);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }
}
