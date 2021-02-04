package com.gagi.market.config.auth;

import com.gagi.market.member.api.dto.SessionMember;
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
            "/api/v1.0/items/search",
            "/api/v1.0/members/login",
            "/api/v1.0/members/duplicated");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionMember session_member = (SessionMember) request.getSession().getAttribute("SESSION_MEMBER");
        if (isGet(request)) {
            return true;
        }
        if (session_member == null) {
            response.setStatus(401);
            response.setHeader("message", "UnAuthorized!!");
            return false;
        }
        return true;
    }

    private boolean isGet(HttpServletRequest request) {
        return (request.getRequestURI().equals("/api/v1.0/items")) && (request.getMethod().equals("GET")) ||
                (request.getRequestURI().equals("/api/v1.0/members")) && (request.getMethod().equals("GET"));
    }
}
