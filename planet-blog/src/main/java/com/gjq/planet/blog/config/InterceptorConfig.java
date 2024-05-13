package com.gjq.planet.blog.config;

import com.gjq.planet.blog.interceptor.AdminInterceptor;
import com.gjq.planet.blog.interceptor.CollectionInterceptor;
import com.gjq.planet.blog.interceptor.TokenInterceptor;
import com.gjq.planet.blog.interceptor.UserStatusInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: gjq0117
 * @date: 2024/4/14 14:46
 * @description: web拦截器配置
 */
@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Autowired
    private UserStatusInterceptor userStatusInterceptor;

    @Autowired
    private CollectionInterceptor collectionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/swagger-ui.html", "/swagger-resources/**", "/webjars/**");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/swagger-ui.html", "/swagger-resources/**", "/webjars/**");
        registry.addInterceptor(userStatusInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/swagger-ui.html", "/swagger-resources/**", "/webjars/**");
        registry.addInterceptor(collectionInterceptor)
                .addPathPatterns("/**");
    }
}
