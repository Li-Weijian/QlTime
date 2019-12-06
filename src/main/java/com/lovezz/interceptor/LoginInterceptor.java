package com.lovezz.interceptor;

import com.lovezz.constant.SystemConstants;
import com.lovezz.entity.TbUser;
import com.lovezz.service.TbUserService;
import com.lovezz.utils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        TbUser tbUser = (TbUser) httpServletRequest.getSession().getAttribute(SystemConstants.SESSION_USER_KEY);

        String activeProfile = SpringContextUtil.getActiveProfile();
        if (tbUser == null && StringUtils.equalsIgnoreCase(activeProfile,SystemConstants.ACTIVE_PROFILE_DEV)){
            //开发环境
            TbUser user = userService.selectById(1);
            tbUser = user;
            //存入session
            httpServletRequest.getSession().setAttribute(SystemConstants.SESSION_USER_KEY,user);
        }

//        return true;
        // 未登录状态
        if (tbUser == null) {
            httpServletRequest.getRequestDispatcher("/user/toLogin").forward(httpServletRequest, httpServletResponse);
            return false;
        }

        // 已登录状态
        else {
            //设置上线时间
            userService.setUserOnline(tbUser.getId());
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
