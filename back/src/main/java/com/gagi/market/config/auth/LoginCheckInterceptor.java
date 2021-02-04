package com.gagi.market.config.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class LoginCheckInterceptor implements HandlerInterceptor {
    public List<String> pathPatters = Arrays.asList(
            "/api/v1.0/items/**",
            "/api/v1.0/members/**");
    public List<String> excludePathPatters = Arrays.asList(
            "/api/v1.0/items",
            "/api/v1.0/items/search",
            "/api/v1.0/members",
            "/api/v1.0/members/login",
            "/api/v1.0/members/duplicated");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object session_member = request.getSession().getAttribute("SESSION_MEMBER");
        if (session_member == null) {
            response.setStatus(407);
            return false;
        }
        return true;
    }
}
