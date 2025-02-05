package com.example.post.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Log 필터 생성");
    }

    // 반드시 구현하는 메서드
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        log.info("doFilter 실행");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        try {
            // 사용자가 요청한 경로에 대한 값을 확인
            log.info("requestURI: {}", requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("필터 실행 중 오류 발생: {}", e.getMessage());
        } finally {
            // 응답 정보 로깅
            log.info("응답 상태 코드: {}", httpResponse.getStatus());
            log.info("응답 컨텐츠 타입: {}", httpResponse.getContentType());
            log.info("응답 헤더 - Content-Language: {}", httpResponse.getHeader("Content-Language"));
            log.info("응답 버퍼 크기: {}", httpResponse.getBufferSize());
        }


        /* 로그인이  필요한 경로인지 확인할 수 있다.
        * ex) users/register, users/login -> 로그인이 필요 없음
              posts/new, posts/update, post/remove, ... -> 로그인일 해야 한다. */
        if (requestURI.contains("posts")) {
            log.info("로그인이 필요한 경로입니다.");
        }


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("log 필터 종료");
    }
}
