package com.lovezz.controller;

import com.lovezz.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class TicController {

    @RequestMapping("/time")
    public ModelAndView TimeRecording(ModelAndView modelAndView) throws IOException {
        List loveList = FileUtils.getTextForLove("/static/love.txt");
        int lineNum = new Random().nextInt(loveList.size()+1);

        modelAndView.addObject("loveMsg",loveList.get(lineNum));
        modelAndView.setViewName("tic/index");

        return modelAndView;
    }

    @RequestMapping("/cleanPage")
    public String cleanPage(){
        return "tic/content/clean";
    }
}
