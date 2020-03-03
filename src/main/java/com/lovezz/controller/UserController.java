package com.lovezz.controller;


import com.lovezz.constant.SystemConstants;
import com.lovezz.dto.BaseResult;
import com.lovezz.entity.TbUser;
import com.lovezz.service.TbUserService;
import com.lovezz.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liweijian
 * @since 2019-10-30
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TbUserService userService;


    /**
     * 登录页
     * @auther: liweijian
     * @date: 2019/11/1 22:31
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "user/login";
    }

    /**
     * 注销
     * @auther: liweijian
     * @date: 2019/11/1 22:31
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().setAttribute(SystemConstants.SESSION_USER_KEY,null);
        return "user/login";
    }

    /**
     * 登录
     * @auther: liweijian
     * @date: 2019/11/1 22:31
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult login(@RequestBody TbUser user, HttpServletRequest request){

        TbUser result  = userService.login(user.getUsername(), user.getPassword());

        if (result == null){
            return BaseResult.fail("用户名或者密码错误");
        }else {

            //存入session
            request.getSession().setAttribute(SystemConstants.SESSION_USER_KEY,result);
            return BaseResult.success("登录成功啦",result);
        }
    }

    /**
     * 登录 - 适配前后端分离架构
     * @auther: liweijian
     */
    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult login2(@RequestBody TbUser user, HttpServletRequest request, HttpServletResponse response){

        TbUser result  = userService.login(user.getUsername(), user.getPassword());

        if (result == null){
            return BaseResult.fail("用户名或者密码错误");
        }else {
            String token = new RequestUtils().generateToken(result);

            Cookie cookie = new Cookie("token", token);
            //不设置路径的话, 会以当前路径为默认值，会导致其他页面不携带该cookie
            cookie.setPath("/");
            cookie.setMaxAge(2147483647);
            response.addCookie(cookie);
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", result);
            return BaseResult.success("登录成功啦",data);
        }
    }

    @RequestMapping(value = "/goodbye")
    public String goodbye(){
        return "user/goodbye";
    }
}

