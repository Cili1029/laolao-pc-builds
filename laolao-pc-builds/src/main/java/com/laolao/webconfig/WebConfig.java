package com.laolao.webconfig;

import com.laolao.Interceptor.SignInInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private SignInInterceptor signInInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/user/email-code")
                .excludePathPatterns("/user/user/sign-in/username")
                .excludePathPatterns("/user/user/sign-in/email")
                .excludePathPatterns("/user/user/sign-up")
                .excludePathPatterns("/user/user/profile");
    }
}
