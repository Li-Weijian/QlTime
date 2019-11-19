package com.lovezz.controller;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.lovezz.service.SendMessageService;
import com.lovezz.utils.FileUtils;
import com.lovezz.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 08:34
 * @Description:
 */
@RestController
@RequestMapping("/sms")
public class SendMessageController {


    @Autowired
    private SendMessageService sendMessageService;
    @Value("${sms.templateid}")
    private String TEMPLATEID;
    @Value("${sms.mobile}")
    private String MOBILE;
    @Value("${love.year}")
    private String YEAR;
    @Value("${love.month}")
    private String MONTH;
    @Value("${love.day}")
    private String DAY;


    @Value("${tx.templateId}")
    private Integer TEMPLATEID2;



    /**
     * 单发短信
     * @Author: liweijian
     */
    @RequestMapping(value = "/sendSms",method = RequestMethod.GET)
    public String sendSms() throws IOException {

        long day = TimeUtils.daysBetween(new Date(Integer.parseInt(YEAR) - 1900, Integer.parseInt(MONTH) - 1, Integer.parseInt(DAY)), new Date());
        List loveList = FileUtils.getTextForLove("/static/love.txt");
        int lineNum = new Random().nextInt(loveList.size()+1);
        String param = day+","+loveList.get(lineNum);

        return sendMessageService.sendMessaage(MOBILE,TEMPLATEID,param);

    }

    /**
     *
     * 腾讯云 短信
     * @auther: liweijian
     * @date: 2019/11/19 17:50
     */
    @RequestMapping(value = "/sendSmsByTX",method = RequestMethod.GET)
    @ResponseBody
    public String sendSmsByTX(@RequestParam(defaultValue = "13078229267") String mobile) {

        String result = null;
        try {
            long day = TimeUtils.daysBetween(new Date(Integer.parseInt(YEAR) - 1900, Integer.parseInt(MONTH) - 1, Integer.parseInt(DAY)), new Date());

            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(day));

            result = sendMessageService.sendMessaage(mobile, TEMPLATEID2, params);

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
