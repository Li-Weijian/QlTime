package com.lovezz.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Auther: liweijian
 * @Date: 2019/10/13 11:23
 * @Description:
 */
public class URLUtils {

    /**
     * 获取域名
     * @param:
     * @return:
     * @auther: liweijian
     * @date: 2019/10/13 11:40
     */
    public static String getUrlHost(String url) throws MalformedURLException {
        URL urlObj = new URL(url);
        String host = urlObj.getHost();
        return urlObj.getProtocol() +"://"+host;
    }

    /**
     * 获取域名+路径（不包括参数）
     * @param:
     * @return:
     * @auther: liweijian
     * @date: 2019/10/13 11:40
     */
    public static String getPath(String url) throws MalformedURLException {
        URL urlObj = new URL(url);
        String host = urlObj.getHost();
        return urlObj.getProtocol() +"://"+host+urlObj.getPath();
    }


//    public static void main(String[] args) throws MalformedURLException {
//        String path = URLUtils.getPath("https://lovezz-app.oss-cn-shenzhen.aliyuncs.com/userImg/1570896976941.png");
//        System.out.println(path);
//    }
}
