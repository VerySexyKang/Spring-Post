package com.example.post.filter.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("로그인 체크 인터셉터 실행");
        // 사용자가 원래 요청했던 URL
        String requestURI = request.getRequestURI();

        // 세션 정보를 가져온다.
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            // 로그인 하지 않은 경우 로그인 페이지로 리다이렉트
            log.info("로그인 하지 않은 사용자입니다.");
            // 원래 가려고했던 URL을 쿼리파라미터로 붙여 보냄
            response.sendRedirect("/users/login?redirectURL=" + requestURI);
            return false;
        }
        // 로그인 완료된 경우
        return true;
    }
}
