package com.qltime.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qltime.dto.BaseResult;
import com.qltime.entity.TbMenu;
import com.qltime.service.TbMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
@Slf4j
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
    public BaseResult getMenuList(@RequestParam(value = "type", defaultValue = "0", required = false) String type){
        log.info("进入 getMenuList");
        List<TbMenu> menuList = menuService.selectList(new EntityWrapper<TbMenu>().eq("type",type).eq("isDelete","0"));
        return BaseResult.success("成功",menuList);
    }

}
