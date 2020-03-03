package com.lovezz.interceptor;

import com.lovezz.constant.SystemConstants;
import com.lovezz.dto.BaseResult;
import com.lovezz.entity.TbUser;
import com.lovezz.service.TbUserService;
import com.lovezz.utils.RequestUtils;
import com.lovezz.utils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * 登录拦截器
 * <p>Title: LoginInterceptor</p>
 * <p>Description: </p>
 *
 * @author liweijian
 * @version 1.0.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TbUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //设置跨域，不设置会导致接收不了cookie
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        Integer userId = new RequestUtils().getLoginUserId();
        if (userId != null){
            //设置上线时间
            userService.setUserOnline(userId);
            return true;
        }else {
           throw new RuntimeException("登录过期");
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
