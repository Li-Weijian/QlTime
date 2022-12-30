package com.qltime.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "wx")
@Component
public class WxConfig {

    private List<WechatTemplate> templateList;
    private MiniApp miniApp;
    private WxPush wxPush;


    /**
     * 小程序
     */
    @Data
    public static class MiniApp {
        private String appid;
        private String appSecret;
    }

    /**
     * 微信推送
     */
    @Data
    public static class WxPush {
        private String appid;
        private String appSecret;
    }

    /**
     * 微信推送模板
     */
    @Data
    public static class WechatTemplate {
        /**
         * 模板类型
         */
        private Integer type;

        /**
         * 模板ID
         */
        private String templateId;
    }



    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(getMiniApp().getAppid());
        config.setSecret(getMiniApp().getAppSecret());
        return config;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }
}
