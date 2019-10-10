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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @Value("${tx.appid}")
    private Integer APPID;
    @Value("${tx.appkey}")
    private String APPKEY;
    @Value("${tx.templateId}")
    private Integer TEMPLATEID2;
    @Value("${tx.smsSign}")
    private String SMSSIGN;


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

    @RequestMapping(value = "/sendSmsByTX",method = RequestMethod.GET)
    public String sendSmsByTX() {
        try {
            long day = TimeUtils.daysBetween(new Date(Integer.parseInt(YEAR) - 1900, Integer.parseInt(MONTH) - 1, Integer.parseInt(DAY)), new Date());
            List loveList = FileUtils.getTextForLove("/static/love.txt");
            int lineNum = new Random().nextInt(loveList.size()+1);

            String[] params = {String.valueOf(day), (String) loveList.get(lineNum)};
            SmsSingleSender ssender = new SmsSingleSender(APPID, APPKEY);
            SmsSingleSenderResult result = ssender.sendWithParam("86", MOBILE,
                    TEMPLATEID2, params, SMSSIGN, "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
        return null;
    }
}
