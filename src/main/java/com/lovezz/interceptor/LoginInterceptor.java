package com.lovezz.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lovezz.constant.MsgCommon;
import com.lovezz.service.TbUserService;
import com.lovezz.utils.IpUtil;
import com.lovezz.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

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
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        //设置跨域，不设置会导致接收不了cookie
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            Integer userId = new RequestUtils().getLoginUserId();
            if (userId != null){
                //设置上线时间
                userService.setUserOnline(userId);
                String ipAddr = IpUtil.getIpAddr(httpServletRequest);
                System.out.println("IP : " + ipAddr + ", 时间: " + LocalDateTime.now() + ", userId: " + userId);

                return true;
            }else {
//                throw new RuntimeException("登录过期");
                returnJson(httpServletResponse);
                return false;
            }
        }catch (Exception e){
            throw new RuntimeException("未知异常");
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void returnJson(HttpServletResponse response){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSON(MsgCommon.TOKEN_ERROR));
        } catch (IOException e){
//            LoggerUtil.logError(ECInterceptor.class, "拦截器输出流异常"+e);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
