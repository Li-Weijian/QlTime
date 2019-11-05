package com.lovezz.utils;

import com.lovezz.constant.SystemConstants;
import com.lovezz.entity.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: liweijian
 * @Date: 2019/11/1 23:05
 * @Description:
 */
public class RequestUtils {

    private HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();


    /**
     *  获取当前登录用户
     * @return: 登录用户
     * @auther: liweijian
     * @date: 2019/11/1 23:08
     */
    public TbUser getLoginUser(){

        return (TbUser) request.getSession().getAttribute(SystemConstants.SESSION_USER_KEY);
    }

    /**
     * 获取当前登录用户id
     * @return: 用户id
     * @auther: liweijian
     * @date: 2019/11/1 23:11
     */
    public Integer getLoginUserId(){
        return getLoginUser().getId();
    }
}
