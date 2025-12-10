package com.laolao.webconfig;

import com.laolao.Interceptor.AdminInInterceptor;
import com.laolao.Interceptor.SignInInterceptor;
import com.laolao.Interceptor.SqlMarkInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private SignInInterceptor signInInterceptor;
    @Resource
    private AdminInInterceptor adminInInterceptor;
    @Resource
    private SqlMarkInterceptor sqlMarkInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/common/user/email-code")
                .excludePathPatterns("/api/common/user/sign-in/username")
                .excludePathPatterns("/api/common/user/sign-in/email")
                .excludePathPatterns("/api/common/user/sign-up")
                .excludePathPatterns("/api/common/user/profile")
                .excludePathPatterns("/api/user/forum/post/hot")
                .excludePathPatterns("/api/user/shop/products/hot")
                .order(1);

        registry.addInterceptor(adminInInterceptor)
                .addPathPatterns("/api/admin/**")
                .order(2);

        registry.addInterceptor(sqlMarkInterceptor)
                .addPathPatterns("/api/admin/**")
                .order(3);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 给所有@RestController添加/api前缀
        configurer.addPathPrefix("/api",
                HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
