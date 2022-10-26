package com.qltime.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.qltime.dto.SmsResult;
import com.qltime.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 11:07
 * @Description:
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TbLovetextServiceImpl lovetextService;

    @Value("${sms.sid}")
    private String SID;
    @Value("${sms.token}")
    private String TOKEN;
    @Value("${sms.appid}")
    private String APPID;
    @Value("${sms.url}")
    private String URL;

    @Value("${tx.appid}")
    private Integer APPID_TX;
    @Value("${tx.appkey}")
    private String APPKEY;
    //    @Value("${tx.smsSign}")
    private String SMSSIGN = "峥峥宝贝专属";


    /**
     * 发送短信
     *
     * @param mobile  手机号
     * @param templateid  模版id
     * @param content  内容，内容请根据模版的顺序，使用 , 进行拼接
     * @return
     */
    @Override
    public String sendMessaage(String mobile, String templateid, String content) throws IOException {
        //单发短信API
        JSONObject jsonObject = new JSONObject();
        //基础配置，在开发平台认证后获取
        jsonObject.put("sid",SID);
        jsonObject.put("token",TOKEN);
        jsonObject.put("appid",APPID);
        //模板ID，在开发平台创建模板对应的模板ID
        jsonObject.put("templateid", templateid);
//        //模板对应的参数，参数之间拼接用逗号作为间隔符
        jsonObject.put("param", content);

        //要发送的手机号
        jsonObject.put("mobile", mobile);
        //用户透传ID，随状态报告返回,可以不填写
        jsonObject.put("uid","");
        String json = JSONObject.toJSONString(jsonObject);
        //使用restTemplate进行访问远程服务
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
        String result = restTemplate.postForObject(URL, httpEntity, String.class);
        return result;
    }


    /**
     * 功能描述: 腾讯云 发送短信
     * @param:
     * @return:
     * @auther: liweijian
     * @date: 2019/10/21 20:11
     */
    @Override
    public SmsResult sendMessaage(String mobile, Integer templateid, ArrayList<String> params) throws IOException, HTTPException {

        String text = lovetextService.getOneTextRandom();

        SmsSingleSender sender = new SmsSingleSender(APPID_TX, APPKEY);
        String one = text.substring(0, text.length() / 2);
        String two = text.substring(text.length() / 2, text.length());
        params.add(one);
        params.add(two);
        SmsSingleSenderResult result =sender.sendWithParam("86", mobile,
                templateid, params, SMSSIGN, "", "");

        return JSONObject.parseObject(result.toString(),SmsResult.class);
    }


}
