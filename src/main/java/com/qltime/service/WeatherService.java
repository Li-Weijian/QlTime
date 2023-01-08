package com.qltime.service;

import com.qltime.model.dto.baidu.Forecasts;
import com.qltime.model.dto.baidu.Location;

/**
 * 天气接口
 * @author liweijian
 * @date 2023/1/8 19:14
 */
public interface WeatherService {

    /**
     * 获取天气
     * @param city
     * @return
     */
    Forecasts getWeatherForecasts(String city);

    Location getLocation(String cityCode);
}
