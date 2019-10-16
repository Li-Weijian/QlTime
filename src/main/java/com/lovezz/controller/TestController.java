package com.lovezz.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.entity.Test;
import com.lovezz.service.TestService;
import com.lovezz.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-09
 */
@Controller
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private OssUtil ossUtil;


    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "测试成功";
    }

    @RequestMapping("/ceshi")
    public String ceshi(){
        return "note/ceshi";
    }


    @RequestMapping("/getTestData")
    @ResponseBody
    public Test getTestData(){
        Test test = testService.getTest();
        return test;
    }

    @RequestMapping("/testImage")
    @ResponseBody
    public void testImage(){
        ossUtil.getImageInfo("https://lovezz-app.oss-cn-shenzhen.aliyuncs.com/userImg/1570896976941.png");
    }

}
