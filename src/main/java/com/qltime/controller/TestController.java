package com.qltime.controller;


import com.qltime.entity.TbLovetext;
import com.qltime.entity.Test;
import com.qltime.service.TbLovetextService;
import com.qltime.service.TestService;
import com.qltime.utils.FileUtils;
import com.qltime.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TbLovetextService lovetextService;


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "测试成功";
    }

    @RequestMapping("/ceshi")
    public String ceshi() {
        return "note/ceshi";
    }


    @RequestMapping("/getTestData")
    @ResponseBody
    public Test getTestData() {
        Test test = testService.getTest();
        return test;
    }

    @RequestMapping("/testImage")
    @ResponseBody
    public void testImage() {
        ossUtil.getImageInfo("https://qltime-app.oss-cn-shenzhen.aliyuncs.com/userImg/1570896976941.png");
    }

    @RequestMapping("/getLoveText")
    @ResponseBody
    public String getLoveText() throws InterruptedException, IOException {
        int count = 0;
        List loveList = FileUtils.getTextForLove("/static/love.txt");

        for (int i = 0; i < loveList.size(); i++) {
            String text = (String) loveList.get(i);
            TbLovetext tbLovetext = new TbLovetext();
            tbLovetext.setText(text);
            tbLovetext.setCount(text.length());
            tbLovetext.setId(null);
            tbLovetext.setDate(new Date());
            lovetextService.insert(tbLovetext);

        }

        return null;
    }
}

