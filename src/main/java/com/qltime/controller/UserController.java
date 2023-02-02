package com.qltime.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.qltime.constant.MsgCommon;
import com.qltime.constant.SystemConstants;
import com.qltime.model.dto.BaseResult;
import com.qltime.model.dto.LoversDto;
import com.qltime.model.dto.WxLoginInfoDto;
import com.qltime.model.entity.TbUser;
import com.qltime.service.TbUserService;
import com.qltime.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@CrossOrigin("*")
@Slf4j
public class UserController {

    @Autowired
    private TbUserService userService;

    @Autowired
    private WxMaService wxMaService;


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
    @Deprecated
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
    @Deprecated
    public BaseResult login2(@RequestBody TbUser user, HttpServletRequest request, HttpServletResponse response){

        TbUser result  = userService.login(user.getUsername(), user.getPassword());

        if (result == null){
            return BaseResult.fail("用户名或者密码错误");
        }else {
            String token = new RequestUtils().generateToken(result);
            response.addCookie(userService.makeCookieByToken(token));
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", result);
            return BaseResult.success("登录成功啦",data);
        }
    }

    /**
     * 微信授权登录
     * @param wxLoginInfo
     * @return
     */
    @PostMapping(value = "wxAuth")
    @ResponseBody
    public BaseResult wxAuth(@RequestBody WxLoginInfoDto wxLoginInfo, HttpServletResponse response){
        try {
            WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(wxLoginInfo.getCode());
            log.info("【微信授权】{}", result.toString());
            wxLoginInfo.setSessionKey(result.getSessionKey());
            wxLoginInfo.setOpenId(result.getOpenid());
            TbUser user = userService.addOrUpdateUser(wxLoginInfo);
            user.setToken(new RequestUtils().generateToken(user));
            return BaseResult.success(MsgCommon.SUCCESS.getMessage(), user);
        } catch (WxErrorException e) {
            log.error("【微信授权】: {}", e.getMessage());
            return BaseResult.fail(e.getMessage());
        }
    }

    /**
     * 获取情侣相关信息
     * @param id
     * @return
     */
    @GetMapping(value = "selectLover/{id}")
    @ResponseBody
    public BaseResult selectLover(@PathVariable("id") Integer id){
        LoversDto loversDto = null;
        try {
            loversDto = userService.selectLover(id);
        } catch (Exception e) {
            log.error("【情侣信息】:{}", e);
            return BaseResult.fail(e.getLocalizedMessage());
        }
        return BaseResult.success(MsgCommon.SUCCESS.getMessage(), loversDto);

    }

    /**
     * 设置在一起的时间
     * @param user
     * @return
     */
    @PostMapping(value = "setTogetherTime")
    @ResponseBody
    public BaseResult setTogetherTime(@RequestBody TbUser user){

        userService.setTogetherTime(user);
        return BaseResult.success();
    }

    /**
     * 关联另一半
     * @param user
     * @return
     */
    @PostMapping(value = "setHalf")
    @ResponseBody
    public BaseResult setHalf(@RequestBody TbUser user){

        return userService.setHalf(user);
    }


}

