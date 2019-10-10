package com.lovezz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lovezz.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 11:07
 * @Description:
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sms.sid}")
    private String SID;
    @Value("${sms.token}")
    private String TOKEN;
    @Value("${sms.appid}")
    private String APPID;
    @Value("${sms.url}")
    private String URL;


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



}
