package com.qltime.service.impl;

import com.qltime.config.ApplicationConfiguration;
import com.qltime.feign.BaiduWeatherRemoteClient;
import com.qltime.model.dto.baidu.BaiduResult;
import com.qltime.model.dto.baidu.BaiduWeatherResult;
import com.qltime.model.dto.baidu.Forecasts;
import com.qltime.model.dto.baidu.Location;
import com.qltime.model.param.BaiduWeatherParam;
import com.qltime.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liweijian
 * @date 2023/1/8 19:15
 */
@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {
    private final ApplicationConfiguration applicationConfiguration;
    private final BaiduWeatherRemoteClient baiduWeatherRemoteClient;

    public WeatherServiceImpl(ApplicationConfiguration applicationConfiguration, BaiduWeatherRemoteClient baiduWeatherRemoteClient) {
        this.applicationConfiguration = applicationConfiguration;
        this.baiduWeatherRemoteClient = baiduWeatherRemoteClient;
    }


    /**
     * 获取天气预报
     * @param cityCode
     * @return
     */
    @Override
    public Forecasts getWeatherForecasts(String cityCode) {
        BaiduResult baiduWeatherResult = getBaiduWeatherResult(cityCode);
        log.info("查询的百度天气信息为：{}", baiduWeatherResult);
        return baiduWeatherResult.getForecasts().get(0);
    }


    /**
     * 获取地址
     * @param cityCode
     * @return
     */
    @Override
    public Location getLocation(String cityCode){
        return getBaiduWeatherResult(cityCode).getLocation();
    }


    private BaiduResult getBaiduWeatherResult(String cityCode) {
        // 获取天气预报信息
        BaiduWeatherParam baiduWeatherParam = BaiduWeatherParam.builder()
            .district_id(cityCode)
            .data_type("all")
            .ak(applicationConfiguration.getBaidu().getAk())
            .build();
        BaiduWeatherResult baiduWeatherResult = baiduWeatherRemoteClient.queryWeather(baiduWeatherParam);
        return baiduWeatherResult.getResult();
    }

}
