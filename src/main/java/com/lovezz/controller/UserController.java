package com.lovezz.controller;


import com.lovezz.constant.SystemConstants;
import com.lovezz.dto.BaseResult;
import com.lovezz.entity.TbUser;
import com.lovezz.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liweijian
 * @since 2019-10-30
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TbUserService userService;


    @RequestMapping("/toLogin")
    public String toLogin(){
        return "user/login";
    }


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
}
