package com.qltime.config;

import com.qltime.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: liweijian
 * @Date: 2019/10/30 16:29
 * @Description:
 */
@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {


    /**
     * 将拦截器交付给spring管理，为了在拦截器中注入对象。
     * @auther: liweijian
     * @date: 2019/11/19 18:29
     */
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 添加拦截的请求，并排除几个不拦截的请求
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/index.html", "/", "/user/toLogin","/ticController/time","/user/login","/static/**","/sms/sendSms","/user/login2", "/user/wxAuth");
    }
}
