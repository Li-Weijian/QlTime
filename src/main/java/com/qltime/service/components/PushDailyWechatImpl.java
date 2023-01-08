package com.qltime.service.components;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.qltime.config.ApplicationConfiguration;
import com.qltime.config.WxConfig;
import com.qltime.feign.TianDataRemoteClient;
import com.qltime.model.dto.tianxin.TianRainbow;
import com.qltime.model.dto.baidu.Forecasts;
import com.qltime.model.dto.tianxin.TianEnsentence;
import com.qltime.model.dto.tianxin.TianStar;
import com.qltime.model.dto.wxpush.WxPushDaily;
import com.qltime.model.entity.TbUser;
import com.qltime.model.param.TianStarParam;
import com.qltime.model.param.TianXinParam;
import com.qltime.service.PushDailyWechat;
import com.qltime.service.TbUserService;
import com.qltime.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.util.*;

/**
 * 推送微信日常
 * @author liweijian
 */
@Slf4j
@Service
public class PushDailyWechatImpl implements PushDailyWechat {

    private final TbUserService userService;

    private final TianDataRemoteClient tianDataRemoteClient;
    private final WeatherService weatherService;

    /**
     * 微信配置类
     **/
    private final WxConfig wxConfig;

    private final ApplicationConfiguration applicationConfiguration;


    public PushDailyWechatImpl(TbUserService userService, TianDataRemoteClient tianDataRemoteClient, WeatherService weatherService, WxConfig wxConfig, ApplicationConfiguration applicationConfiguration) {
        this.userService = userService;
        this.tianDataRemoteClient = tianDataRemoteClient;
        this.weatherService = weatherService;
        this.wxConfig = wxConfig;
        this.applicationConfiguration = applicationConfiguration;
    }


    /**
     * 给不同的用户推送消息
     */
    @Override
    public void pushWechat() {
        // 获取用户列表
        List<TbUser> userInfoList = userService.listUserInfo();
        if (!CollectionUtils.isEmpty(userInfoList)) {
            // 根据用户的type类型和模板type进行匹配
            for (TbUser userInfo : userInfoList) {
                if (StringUtils.isEmpty(userInfo.getWechatId())){
                    continue;
                }
                for (WxConfig.WechatTemplate template : wxConfig.getTemplateList()) {
                    if (Optional.of(userInfo).map(TbUser::getGender).orElse(0).equals(template.getType())) {
                        this.wechatData(userInfo.getWechatId(), template.getTemplateId(), userInfo);
                    }
                }
            }
        }
    }



    /**
     * 封装微信数据
     *
     * @param wechatId
     * @param templateId
     */
    private void wechatData(String wechatId, String templateId, TbUser userInfo) {

        // 获取天气
        Forecasts todayForecasts = weatherService.getWeatherForecasts(userInfo.getCity());

        // 获取彩虹屁
        String tianXinKey = applicationConfiguration.getTianXin().getKey();
        TianXinParam tianXinParam = new TianXinParam().setKey(tianXinKey);
        TianRainbow.Result rainbow = tianDataRemoteClient.queryRainbow(tianXinParam).getNewslist().get(0);

        // 获取每日一句
        TianEnsentence.Result ensentence = tianDataRemoteClient.queryEnsentence(tianXinParam).getNewslist().get(0);

        // 获取星座运势
        List<TianStar.Result> star = tianDataRemoteClient.queryStar(new TianStarParam().setAstro(userInfo.getStar()).setKey(tianXinKey)).getNewslist();

        pushDaily(new WxPushDaily(wechatId, templateId)
            .setNow(todayForecasts)
            .setCity(weatherService.getLocation(userInfo.getCity()).getCity())
            .setWeather(todayForecasts.getText_day())
            .setHighTemperature(String.valueOf(todayForecasts.getHigh()))
            .setLowTemperature(String.valueOf(todayForecasts.getLow()))
            .setScqDay(this.calScqDate(userInfo))
            .setDailyEnglishCn(ensentence.getNote())
            .setDailyEnglishEn(ensentence.getContent())
            .setRainbow(rainbow.getContent())
            .setHoroscope(star)
            .setStar(userInfo.getStar())
        );

    }


    /**
     * 计算想认识/想恋日期
     *
     * @return
     */
    private String calScqDate(TbUser userInfo) {
        // 获取第一次想认识的时间
        if (Objects.nonNull(userInfo)) {
            Date scqTime = userInfo.getTogetheTime();
            // 计算时间差
            long between = DateUtil.between(scqTime, DateUtil.date(), DateUnit.DAY);
            return String.valueOf(between);
        }
        return "";
    }


    /**
     * 拼接今日时间
     *
     * @return
     */
    private String pareDateNow(Forecasts baiduForecastsWeather) {
        // 获取当前日期
        String now = DateUtil.format(DateUtil.date(), DatePattern.CHINESE_DATE_PATTERN);
        // 获取星期几
        String week = baiduForecastsWeather.getWeek();
        return now + " " + week;
    }

    /**
     * 每日推送
     * @param wxPushDaily
     */
    private void pushDaily(WxPushDaily wxPushDaily){

        // 创建配置信息
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();

        wxStorage.setAppId(wxConfig.getWxPush().getAppid());
        wxStorage.setSecret(wxConfig.getWxPush().getAppSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        // 创建模板信息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
            .toUser(wxPushDaily.getWechatId())
            .templateId(wxPushDaily.getTemplateId())
            .build();

        // 封装模板数据
        templateMessage.addData(new WxMpTemplateData("now", this.pareDateNow(wxPushDaily.getNow()), "#FFB6C1"));
        templateMessage.addData(new WxMpTemplateData("city", wxPushDaily.getCity(), "#B95EA6"));
        templateMessage.addData(new WxMpTemplateData("weather", wxPushDaily.getWeather(), "#173177"));
        templateMessage.addData(new WxMpTemplateData("high", wxPushDaily.getHighTemperature(), "#87cefa"));
        templateMessage.addData(new WxMpTemplateData("low", wxPushDaily.getLowTemperature(), "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("scq_day", wxPushDaily.getScqDay(), "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("daily_english_cn", wxPushDaily.getDailyEnglishCn(), "#800080"));
        templateMessage.addData(new WxMpTemplateData("daily_english_en", wxPushDaily.getDailyEnglishEn(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("rainbow", wxPushDaily.getRainbow(), "#FFA500"));

        // 运势
        buildStarData(templateMessage, wxPushDaily.getStar(), wxPushDaily.getHoroscope());

        log.info("发送的消息为：{}", JSON.toJSONString(templateMessage));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("[微信每日推送]. wechatId: {}, templateId: {}", wxPushDaily.getWechatId(), wxPushDaily.getTemplateId(), e);
        }
    }

    /**
     * 构建星座运势模板
     * @param templateMessage
     * @param star
     * @param horoscope
     * @return
     */
    private void buildStarData(WxMpTemplateMessage templateMessage, String star, List<TianStar.Result> horoscope) {

        templateMessage.addData(new WxMpTemplateData("composite", horoscope.get(0).getContent(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("star", star, "#FFFFFF"));
        templateMessage.addData(new WxMpTemplateData("overview", horoscope.get(8).getContent(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("love", horoscope.get(1).getContent(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("work", horoscope.get(2).getContent(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("wealth", horoscope.get(4).getContent(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("healthy", horoscope.get(3).getContent(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("lucky", horoscope.get(5).getContent(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("lucky_number", horoscope.get(6).getContent(), "#FFA500"));
    }
}
