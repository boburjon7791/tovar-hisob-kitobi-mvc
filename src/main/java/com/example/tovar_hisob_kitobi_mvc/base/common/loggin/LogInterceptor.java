package com.example.tovar_hisob_kitobi_mvc.base.common.loggin;

import com.example.tovar_hisob_kitobi_mvc.base.common.security.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Slf4j
@Configuration
public class LogInterceptor implements HandlerInterceptor, WebMvcConfigurer {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Arrays.stream(SecurityConfig.OPEN).map(path -> path.replaceAll("\\*\\*",""))
                .filter(path -> request.getRequestURI().contains(path))
                .findAny()
                .isEmpty()) {
            log.info("handled : {} {} {}",request.getRequestURL(), request.getMethod(), request.getHeader(HttpHeaders.USER_AGENT));
        }
        return true;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }
}
