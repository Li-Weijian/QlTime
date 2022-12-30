package com.qltime.model.param;

import lombok.Builder;
import lombok.Data;

/**
 *  百度天气请求参数
 * @author liweijian
 * @date 2022/12/29 17:22
 */
@Data
@Builder
public class BaiduWeatherParam {

    /**
     * 区县的行政区划编码，和location二选一
     */
    private String district_id;

    /**
     * 经纬度，经度在前纬度在后，逗号分隔。支持类型：bd09mc/bd09ll/wgs84/gcj02。开通高级权限后才能使用
     */
    private Double location;


    /**
     * 开发者密钥，可在API控制台申请获得
     */
    private String ak;

    /**
     * 请求数据类型。数据类型有：now/fc/index/alert/fc_hour/all，控制返回内容
     */
    private String data_type;

    /**
     *  返回格式，默认Json,目前支持json/xml
     */
    private String output;
}
