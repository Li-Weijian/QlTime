package com.lovezz.config;

import com.lovezz.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: liweijian
 * @Date: 2019/10/30 16:29
 * @Description:
 */
@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截的请求，并排除几个不拦截的请求
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/index.html", "/", "/user/toLogin","/ticController/time","/user/login","/static/**","/sms/sendSms");
    }
}
