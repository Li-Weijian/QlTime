package com.lovezz.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {

    @Value("${wx.miniapp.appid}")
    private String APP_ID;

    @Value("${wx.miniapp.appSecret}")
    private String APP_SECRET;

    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(APP_ID);
        config.setSecret(APP_SECRET);
        return config;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }
}
