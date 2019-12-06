package com.lovezz.controller;

import com.lovezz.dto.BaseResult;
import com.lovezz.dto.TopsDTO;
import com.lovezz.entity.TbTops;
import com.lovezz.service.TbTopsService;
import com.lovezz.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liweijian
 * @Date: 2019/11/3 17:22
 * @Description: 留言板控制器
 */
@Controller
@RequestMapping("/tops")
public class TopsController {


    @Autowired
    private TbTopsService topsService;

    @RequestMapping("/toTops")
    public ModelAndView toTops(ModelAndView modelAndView){
        List<TopsDTO> topsList = topsService.getTopsList();

        modelAndView.addObject("topsList", topsList);
        modelAndView.setViewName("tops/index2");

        return modelAndView;
    }

    @RequestMapping("/toPublish")
    public String toPublish(){

        return "tops/publish";
    }

    @RequestMapping("/publishTops")
    @ResponseBody
    public Map<String,Object> publishTops(@RequestParam("file") MultipartFile[] file, @RequestParam("topText") String topText) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();


        boolean isSucc = topsService.publishTops(file,topText);
        if (isSucc){
            resultMap.put("code",200);
            resultMap.put("message","发表成功");
        }else {
            resultMap.put("code",500);
            resultMap.put("message","发表失败");
        }

        return resultMap;
    }

    @RequestMapping("/deleteTops")
    @ResponseBody
    public BaseResult deleteTops(String topsId){
        return topsService.deleteTops(topsId);

    }




}
