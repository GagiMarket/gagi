package com.gagi.market.config.auth;

import com.gagi.market.member.api.dto.SessionMember;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String SESSION_MEMBER = "SESSION_MEMBER";
    private final HttpSession httpSession;

    public LoginMemberArgumentResolver(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (checkLoginMemberAnnotation(parameter) && checkSessionMemberClass(parameter));
    }

    private boolean checkLoginMemberAnnotation(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    private boolean checkSessionMemberClass(MethodParameter parameter) {
        return SessionMember.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute(SESSION_MEMBER);
    }
}
