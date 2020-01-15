package com.lovezz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.dto.BaseResult;
import com.lovezz.entity.TbMenu;
import com.lovezz.service.TbMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Auther: liweijian
 * @Date: 2019/12/6 19:57
 * @Description:
 */
@Controller
@RequestMapping("/menu")
@CrossOrigin("*")
public class MenuController {

    @Autowired
    private TbMenuService menuService;

    @RequestMapping("/toMenu")
    public ModelAndView toMenu(ModelAndView modelAndViewd){
        List<TbMenu> menuList = menuService.selectList(new EntityWrapper<TbMenu>().eq("isDelete", "0"));

        modelAndViewd.addObject("menuList",menuList);
        modelAndViewd.setViewName("menu/index");
        return modelAndViewd;
    }

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/getMenuList")
    @ResponseBody
    public BaseResult getMenuList(){
        List<TbMenu> menuList = menuService.selectList(new EntityWrapper<TbMenu>().eq("isDelete", "1"));
        return BaseResult.success("成功",menuList);
    }
}
