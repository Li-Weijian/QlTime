package com.qltime.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liweijian
 * @date 2022/12/29 17:54
 */
@ConfigurationProperties(prefix = "application")
@Component
@Data
public class ApplicationConfiguration {

    /**
     * 百度
     */
    private Baidu baidu;

    /**
     * 天行数据 https://www.tianapi.com/console/
     */
    private TianXin tianXin;

    /**
     * 新内容是否发送通知
     */
    private Boolean notice;

    @Data
    public static class Baidu {

        private String server;

        private String ak;

    }


    @Data
    public static class TianXin {

        private String server;

        private String key;

    }
}
