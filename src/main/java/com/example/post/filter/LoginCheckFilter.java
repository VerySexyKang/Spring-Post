package com.example.post.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

// 필터는 Filter 인터페이스를 구현한다.
@Slf4j
public class LoginCheckFilter implements Filter {

    // 로그인이 필요하지 않은 경로를 정리
    private static final String[] whitelist = {
            "/",
            "/users/register",
            "/users/login",
            "/users/logout"};

    // 화이트리스트의 경우에는 인증체크를 하지 않는다.
    public boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.info("login check filter 실행");

        // 유저 정보 가져옴
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 1. 사용자가 요청한 URL 정보(로그인이 필요한 요청인지 확인하기 위함)
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("로그인 체크 필터 시작");
            // 사용자가 요청한 URL이 로그인 체크가 필요 한지 확인
            if (isLoginCheckPath(requestURI)) {
                // 필요하면 세션에서 로그인 정보를 받아온다
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute("loginUser") == null) {
                    // 로그인 정보가 없으면
                    log.info("인증되지 않은 사용자");
                    //로그인페이지로 리다이렉트한다.
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    // HttpServletResponse에서 제공하는 리다이렉트 메소드(sendRedirect)
                    httpResponse.sendRedirect("/users/login");
                    // 리턴을 하지 않으면 다음 필터로 계속 진행한다.
                    return;
                }

            }
            // 로그인 체크가 필요하지 않거나 로그인 정보가 있으면 다음 필터로 이동한다.
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("로그인 체크 필터 종료");
        }

        // 다음 필터로 실행 순서를 넘긴다

    }
}
