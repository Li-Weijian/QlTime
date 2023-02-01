package com.qltime.utils;

import com.qltime.constant.SystemConstants;
import com.qltime.model.entity.TbUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: liweijian
 * @Date: 2019/11/1 23:05
 * @Description:
 */
public class RequestUtils {

    private static ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    private static HttpServletRequest request = attributes.getRequest();


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
     * 通过token获取用户id - 适配前后端分离架构
     */
    public static Integer getLoginUserId(){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (SystemConstants.TOKEN.equalsIgnoreCase(cookie.getName())){
                    return Integer.parseInt(cookie.getValue().split("\\|")[0]);
                }
            }
        }
        return null;
    }

    /**
     * 生成token
     * @param user
     */
    public String generateToken (TbUser user){
        return user.getId() + "|" + DigestUtils.sha1Hex(user.getId() + user.getUsername());
    }


}
