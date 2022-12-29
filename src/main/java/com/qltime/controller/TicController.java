package com.qltime.controller;

import com.qltime.dto.BaseResult;
import com.qltime.service.TbLovetextService;
import com.qltime.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @Auther: liweijian
 * @Date: 2019/7/29 16:52
 * @Description:
 */
@Controller
@RequestMapping("/ticController")
@CrossOrigin("*")
public class TicController {

    @Autowired
    private TbLovetextService lovetextService;

    @RequestMapping("/time")
    public ModelAndView TimeRecording(ModelAndView modelAndView) throws IOException {
        List loveList = FileUtils.getTextForLove("/static/love.txt");
        int lineNum = new Random().nextInt(loveList.size());

        modelAndView.addObject("loveMsg",loveList.get(lineNum));
        modelAndView.setViewName("tic/index");

        return modelAndView;
    }

    @RequestMapping("/cleanPage")
    public String cleanPage(){
        return "tic/content/clean";
    }

    @RequestMapping("/getLoveText")
    @ResponseBody
    public BaseResult getLoveText(){
        return BaseResult.success("成功",lovetextService.getOneTextRandom());
    }

}
