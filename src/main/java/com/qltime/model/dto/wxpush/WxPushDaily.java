package com.qltime.model.dto.wxpush;

import com.qltime.model.dto.baidu.Forecasts;
import com.qltime.model.dto.tianxin.TianStar;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @author liweijian
 * @date 2023/1/8 19:33
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class WxPushDaily extends BaseWxPush {

    public WxPushDaily(String wechatId, String templateId) {
        super(wechatId, templateId);
    }

    /**
     * 日期
     */
    private Forecasts now;

    /**
     * 地区
     */
    private String city;

    /**
     * 天气
     */
    private String weather;

    /**
     * 最高气温
     */
    private String highTemperature;

    /**
     * 最低气温
     */
    private String lowTemperature;

    /**
     * 在一起时间
     */
    private String scqDay;

    /**
     * 每日英语句子 - 中文
     */
    private String dailyEnglishCn;

    /**
     * 每日英语句子 - 英文
     */
    private String dailyEnglishEn;

    /**
     * 彩虹屁
     */
    private String rainbow;

    /**
     * 星座
     */
    private String star;

    /**
     * 星座运势
     */
    private List<TianStar.Result> horoscope;


}
