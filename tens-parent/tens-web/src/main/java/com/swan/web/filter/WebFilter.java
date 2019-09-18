package com.swan.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 解决经过zuul网管请求头数据丢失的问题
 */
@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取requst context上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 通过zuul获取request域
        HttpServletRequest request = context.getRequest();
        // 获取请求头header
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization)) {
            // 传递请求头header
            context.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
