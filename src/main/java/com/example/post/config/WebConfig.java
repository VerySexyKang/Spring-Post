package com.example.post.config;

import com.example.post.filter.LogFilter;
import com.example.post.filter.LoginCheckFilter;
import com.example.post.filter.interceptor.LogInterceptor;
import com.example.post.filter.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 스프링 설정과 관련된 클래스
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // @Configuration 클래스의 @Bean 메서드의 리턴 값이 스프링으로 등록 된다.
    // logFilter 메서드가 실행되면서 Bean으로 등록 됨. Bean 어노테이션만 주석처리하면 필터 실행 안됨
//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        // 사용할 필터를 지정한다. (기존에 만든 LogFilter 클래스)
        filterRegistrationBean.setFilter(new LogFilter());
        // 필터의 순서를 지정. 숫자가 낮을수록 먼저 동작한다.
        filterRegistrationBean.setOrder(1);
        // 필터를 적용할 URL 패턴을 지정한다.
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        // 사용할 필터를 지정한다. (기존에 만든 LogFilter 클래스)
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        // 필터의 순서를 지정. 숫자가 낮을수록 먼저 동작한다.
        filterRegistrationBean.setOrder(2);
        // 필터를 적용할 URL 패턴을 지정한다.
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    // 인터셉터 등록
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(new LogInterceptor())
////                // 인터셉터의 실행 순서 지정
////                .order(1)
////                // 인터셉터를 적용할 URL 패턴
////                .addPathPatterns("/**");
//
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                // 제외할 경로 지정 (excludePathPatterns)
//                .excludePathPatterns("/", "/users/register", "/users/login", "/users/logout");
//    }

}
