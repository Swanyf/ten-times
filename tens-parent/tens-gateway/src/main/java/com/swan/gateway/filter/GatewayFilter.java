package com.swan.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class GatewayFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 前置过滤器
     * pre ：可以在请求被路由之前调用
     * post : 在route和error过滤器之后被调用
     * route ：在路由请求时候被调用
     * error ：处理请求时发生错误时被调用
     */
    @Override
    public String filterType() {
        return "pre";   // 开始之前过滤
    }

    /**
     * 多个过滤器的执行顺序，数字越小越小执行
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器执行的操作
     *
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("过滤器执行中...");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String header = request.getHeader("Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }

//        String url = request.getRequestURI();
//        if (url.indexOf("/login") > 0) {
//            System.out.println("登陆页面" + url);
//            return null;
//        }

        if (StringUtils.isNotBlank(header)) {
//            if (header.startsWith("token:")) {
                String token = header.substring(6);
                Claims claims = jwtUtil.parseJWT(header);
                if (claims != null) {
                    if (Objects.equals(claims.get("roles"), "admin")) {
                        context.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                }
//            }
        }
        context.setSendZuulResponse(false);//终止运行
        context.setResponseStatusCode(401);//http状态码
        context.setResponseBody("无权访问");
        context.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }

}
