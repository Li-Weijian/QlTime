package com.qltime.service.components;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.qltime.config.ApplicationConfiguration;
import com.qltime.config.WxConfig;
import com.qltime.feign.BaiduWeatherRemoteClient;
import com.qltime.feign.TianDataRemoteClient;
import com.qltime.model.dto.TianRainbow;
import com.qltime.model.dto.baidu.BaiduWeatherResult;
import com.qltime.model.dto.baidu.Forecasts;
import com.qltime.model.dto.baidu.Now;
import com.qltime.model.dto.tianxin.TianEnsentence;
import com.qltime.model.entity.TbUser;
import com.qltime.model.param.BaiduWeatherParam;
import com.qltime.model.param.TianXinParam;
import com.qltime.service.PushDailyWechat;
import com.qltime.service.TbUserService;
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

@Slf4j
@Service
public class PushDailyWechatImpl implements PushDailyWechat {

    private final TbUserService userService;
    private final BaiduWeatherRemoteClient baiduWeatherRemoteClient;
    private final TianDataRemoteClient tianDataRemoteClient;

    /**
     * 微信配置类
     **/
    private final WxConfig wxConfig;

    private final ApplicationConfiguration applicationConfiguration;


    public PushDailyWechatImpl(TbUserService userService, BaiduWeatherRemoteClient baiduWeatherRemoteClient, TianDataRemoteClient tianDataRemoteClient, WxConfig wxConfig, ApplicationConfiguration applicationConfiguration) {
        this.userService = userService;
        this.baiduWeatherRemoteClient = baiduWeatherRemoteClient;
        this.tianDataRemoteClient = tianDataRemoteClient;
        this.wxConfig = wxConfig;
        this.applicationConfiguration = applicationConfiguration;
    }


    /**
     * 给不同的用户推送消息
     */
    @Override
    public void pushWechat() throws WxErrorException {
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
    private void wechatData(String wechatId, String templateId, TbUser userInfo) throws WxErrorException {
        // 创建配置信息
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();

        wxStorage.setAppId(wxConfig.getWxPush().getAppid());
        wxStorage.setSecret(wxConfig.getWxPush().getAppSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        // 创建模板信息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
            .toUser(wechatId)
            .templateId(templateId)
            .build();

        // 获取天气预报信息
        BaiduWeatherParam baiduWeatherParam = BaiduWeatherParam.builder()
            .district_id(userInfo.getCity())
            .data_type("all")
            .ak(applicationConfiguration.getBaidu().getAk())
            .build();
        BaiduWeatherResult baiduWeatherResult = baiduWeatherRemoteClient.queryWeather(baiduWeatherParam);
        log.info("查询的百度天气信息为：{}", baiduWeatherResult);
        List<Forecasts> baiduForecastsWeatherList = baiduWeatherResult.getResult().getForecasts();

        // 获取彩虹屁
        TianXinParam tianXinParam = TianXinParam.builder().key(applicationConfiguration.getTianXin().getKey()).build();
        TianRainbow.Result rainbow = tianDataRemoteClient.queryRainbow(tianXinParam).getNewslist().get(0);

        // 获取每日一句
        TianEnsentence.Result ensentence = tianDataRemoteClient.queryEnsentence(tianXinParam).getNewslist().get(0);

        // 封装模板数据
        Now now = baiduWeatherResult.getResult().getNow();
        Forecasts todayForecasts = baiduForecastsWeatherList.get(0);
        templateMessage.addData(new WxMpTemplateData("now", this.pareDateNow(todayForecasts), "#FFB6C1"));
        templateMessage.addData(new WxMpTemplateData("city", baiduWeatherResult.getResult().getLocation().getCity(), "#B95EA6"));
        templateMessage.addData(new WxMpTemplateData("text", todayForecasts.getText_day(), "#173177"));
        templateMessage.addData(new WxMpTemplateData("high", String.valueOf(todayForecasts.getHigh()), "#87cefa"));
        templateMessage.addData(new WxMpTemplateData("low", String.valueOf(todayForecasts.getLow()), "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("scq_day", this.calScqDate(userInfo), "#FF1493"));
//        templateMessage.addData(new WxMpTemplateData("bir_day", this.calBirData(userInfo), "#FF00FF"));
        templateMessage.addData(new WxMpTemplateData("bir_day", "520", "#FF00FF"));
        templateMessage.addData(new WxMpTemplateData("daily_english_cn", ensentence.getNote(), "#800080"));
        templateMessage.addData(new WxMpTemplateData("daily_english_en", ensentence.getContent(), "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("rainbow", rainbow.getContent(), "#FFA500"));

        log.info("发送的消息为：{}", JSON.toJSONString(templateMessage));
        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
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
     * 计算生日
     *
     * @param userInfo
     * @return
     */
/*    private String calBirData(UserInfo userInfo) {
        // 获取用户的出生日期
        if (Objects.nonNull(userInfo)) {
            Date birTime = userInfo.getBirTime();
            // 今日日期
            Calendar today = Calendar.getInstance();
            // 出生日期
            Calendar birthDay = Calendar.getInstance();
            // 设置生日
            birthDay.setTime(birTime);
            // 修改为本年
            int bir;
            birthDay.set(Calendar.YEAR, today.get(Calendar.YEAR));
            if (birthDay.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR)) {
                // 生日已经过了，计算明年的
                bir = today.getActualMaximum(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR);
                bir += birthDay.get(Calendar.DAY_OF_YEAR);
            } else {
                // 生日还没过
                bir = birthDay.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR);
            }
            return String.valueOf(bir);
        }
        return "";
    }*/


    /**
     * 拼接今日时间
     *
     * @return
     * @param baiduForecastsWeather
     */
    private String pareDateNow(Forecasts baiduForecastsWeather) {
        // 获取当前日期
        String now = DateUtil.format(DateUtil.date(), DatePattern.CHINESE_DATE_PATTERN);
        // 获取星期几
        String week = baiduForecastsWeather.getWeek();
        return now + " " + week;
    }
}
