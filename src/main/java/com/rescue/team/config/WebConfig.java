package com.rescue.team.config;

import com.rescue.team.filter.CORSInterceptor;
import com.rescue.team.filter.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CORSInterceptor corsInterceptor;

    @Autowired
    private RequestInterceptor requestInterceptor;

    String[] loginPaths=new String[]{
            "/**"
    };
    String[] unLoginPaths=new String[]{
            "/user/getMsgCode",
            "/user/loginByPassword",
            "/user/loginByMessage",
            "/user/register",
            "/user/refreshToken",
            "/file/uploadFile",
            "/error",
            "/static/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/swagger-ui.html",
            "**/websocket/**",
            "/third/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
        registry.addInterceptor(requestInterceptor).addPathPatterns(loginPaths).excludePathPatterns(unLoginPaths);
    }
}
