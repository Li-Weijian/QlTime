package com.lovezz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Auther: liweijian
 * @Date: 2019/11/3 17:22
 * @Description: 留言板控制器
 */
@Controller
@RequestMapping("/tops")
public class TopsController {

    @RequestMapping("/toTops")
    public String toTops(){

        return "tops/index";
    }




}
