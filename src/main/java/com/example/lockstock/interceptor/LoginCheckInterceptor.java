package com.example.lockstock.interceptor;

import com.example.lockstock.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.USER_ID) == null) {
            response.sendRedirect("/login"); // 로그인 안 했으면 로그인 페이지로!
            return false; // 컨트롤러 실행 중단
        }
        return true; // 로그인 했으면 통과!
    }
}