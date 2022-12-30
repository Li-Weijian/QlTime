/**
 * Copyright 2022 json.cn
 */
package com.qltime.model.dto.baidu;

import lombok.Data;

/**
 * Auto-generated: 2022-12-29 18:41:36
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class BaiduWeatherResult {

    private int status;
    private BaiduResult result;
    private String message;
}

/*


{
    "status":0,
    "result":{
    "location":{
    "country":"中国",
    "province":"北京市",
    "city":"北京市",
    "name":"东城",
    "id":"110101"
    },
    "now":{
    "temp":4,
    "feels_like":1,
    "rh":73,
    "wind_class":"2级",
    "wind_dir":"东风",
    "text":"多云",
    "prec_1h":0,
    "clouds":999999,
    "vis":3471,
    "aqi":140,
    "pm25":107,
    "pm10":0,
    "no2":23,
    "so2":22,
    "o3":70,
    "co":1.7,
    "uptime":"20200220143500"
    },
    "indexes":[
    {
    "name":"晨练指数",
    "brief":"较适宜",
    "detail":"天气阴沉，请避免在林中晨练。"
    },
    {
    "name":"洗车指数",
    "brief":"适宜",
    "detail":"天气较好，适合擦洗汽车。"
    },
    {
    "name":"感冒指数",
    "brief":"易发",
    "detail":"天凉，昼夜温差大，易感冒"
    },
    {
    "name":"紫外线指数",
    "brief":"最弱",
    "detail":"辐射弱，涂擦SPF8-12防晒护肤品。"
    },
    {
    "name":"穿衣指数",
    "brief":"较冷",
    "detail":"建议着厚外套加毛衣等服装。"
    },
    {
    "name":"运动指数",
    "brief":"较适宜",
    "detail":"气温较低，在户外运动请注意增减衣物。"
    }
    ],
    "alerts":[
    {
    "type": "道路冰雪",
    "level": "蓝色预警",
    "title": "市气象局发布道路冰雪蓝色预警[IV级/一般]",
    "desc": "市气象局发布道路冰雪蓝色预警信号:受降雪天气影响，
    预计未来 24 小时我市将出现对交通有影响的道路结冰或积雪，
    请有关部门及广大群众做好防范工作。"
    }

    ],
    "forecasts":[
    {
    "date":"2020-02-20",
    "week":"星期四",
    "high":7,
    "low":-2,
    "wc_day":"<3级",
    "wc_night":"<3级",
    "wd_day":"东南风",
    "wd_night":"北风",
    "text_day":"多云",
    "text_night":"阴",
    "aqi":93
    },
    {
    "date":"2020-02-21",
    "week":"星期五",
    "high":11,
    "low":1,
    "wc_day":"4~5级",
    "wc_night":"<3级",
    "wd_day":"西北风",
    "wd_night":"西北风",
    "text_day":"多云",
    "text_night":"晴",
    "aqi":44
    },
    {
    "date":"2020-02-22",
    "week":"星期六",
    "high":10,
    "low":-2,
    "wc_day":"<3级",
    "wc_night":"<3级",
    "wd_day":"西风",
    "wd_night":"南风",
    "text_day":"晴",
    "text_night":"晴",
    "aqi":39
    },
    {
    "date":"2020-02-23",
    "week":"星期日",
    "high":11,
    "low":0,
    "wc_day":"<3级",
    "wc_night":"<3级",
    "wd_day":"北风",
    "wd_night":"北风",
    "text_day":"晴",
    "text_night":"晴",
    "aqi":65
    },
    {
    "date":"2020-02-24",
    "week":"星期一",
    "high":9,
    "low":-1,
    "wc_day":"<3级",
    "wc_night":"<3级",
    "wd_day":"东风",
    "wd_night":"东北风",
    "text_day":"多云",
    "text_night":"多云",
    "aqi":38
    },
    {
    "date":"2020-02-25",
    "week":"星期二",
    "high":9,
    "low":-3,
    "wc_day":"<3级",
    "wc_night":"<3级",
    "wd_day":"东南风",
    "wd_night":"西南风",
    "text_day":"晴",
    "text_night":"晴",
    "aqi":27
    },
    {
    "date":"2020-02-26",
    "week":"星期三",
    "high":9,
    "low":-3,
    "wc_day":"<3级",
    "wc_night":"<3级",
    "wd_day":"西南风",
    "wd_night":"西南风",
    "text_day":"晴",
    "text_night":"晴",
    "aqi":26
    }
    ],
    "forecast_hours":[
    {
    "text":"晴",
    "temp_fc":14,
    "wind_class":"3~4级",
    "wind_dir":"西南风",
    "rh":15,
    "prec_1h":0,
    "clouds":10,
    "data_time":"2020-04-01 16:00:00"
    },
    {
    "text":"晴",
    "temp_fc":14,
    "wind_class":"3~4级",
    "wind_dir":"西南风",
    "rh":13,
    "prec_1h":0,
    "clouds":10,
    "data_time":"2020-04-01 17:00:00"
    },
    {
    "text":"晴",
    "temp_fc":13,
    "wind_class":"<3级",
    "wind_dir":"西南风",
    "rh":14,
    "prec_1h":0,
    "clouds":10,
    "data_time":"2020-04-01 18:00:00"
    },
    {
    "text":"晴",
    "temp_fc":11,
    "wind_class":"<3级",
    "wind_dir":"西南风",
    "rh":15,
    "prec_1h":0,
    "clouds":10,
    "data_time":"2020-04-01 19:00:00"
    },
    {
    "text":"晴",
    "temp_fc":10,
    "wind_class":"<3级",
    "wind_dir":"西南风",
    "rh":16,
    "prec_1h":0,
    "clouds":10,
    "data_time":"2020-04-01 20:00:00"
    },
    {
    "text":"晴",
    "temp_fc":9,
    "wind_class":"<3级",
    "wind_dir":"西风",
    "rh":18,
    "prec_1h":0,
    "clouds":6,
    "data_time":"2020-04-01 21:00:00"
    },
    {
    "text":"晴",
    "temp_fc":9,
    "wind_class":"<3级",
    "wind_dir":"西风",
    "rh":20,
    "prec_1h":0,
    "clouds":3,
    "data_time":"2020-04-01 22:00:00"
    },
    {
    "text":"晴",
    "temp_fc":8,
    "wind_class":"<3级",
    "wind_dir":"西风",
    "rh":21,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-01 23:00:00"
    },
    {
    "text":"晴",
    "temp_fc":7,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":26,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 00:00:00"
    },
    {
    "text":"晴",
    "temp_fc":6,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":31,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 01:00:00"
    },
    {
    "text":"晴",
    "temp_fc":6,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":36,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 02:00:00"
    },
    {
    "text":"晴",
    "temp_fc":5,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":39,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 03:00:00"
    },
    {
    "text":"晴",
    "temp_fc":4,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":42,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 04:00:00"
    },
    {
    "text":"晴",
    "temp_fc":4,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":45,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 05:00:00"
    },
    {
    "text":"晴",
    "temp_fc":5,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":40,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 06:00:00"
    },
    {
    "text":"晴",
    "temp_fc":7,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":34,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 07:00:00"
    },
    {
    "text":"晴",
    "temp_fc":8,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":29,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 08:00:00"
    },
    {
    "text":"晴",
    "temp_fc":11,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":29,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 09:00:00"
    },
    {
    "text":"晴",
    "temp_fc":13,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":29,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 10:00:00"
    },
    {
    "text":"晴",
    "temp_fc":16,
    "wind_class":"<3级",
    "wind_dir":"西北风",
    "rh":29,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 11:00:00"
    },
    {
    "text":"晴",
    "temp_fc":17,
    "wind_class":"3~4级",
    "wind_dir":"西北风",
    "rh":24,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 12:00:00"
    },
    {
    "text":"晴",
    "temp_fc":18,
    "wind_class":"3~4级",
    "wind_dir":"西北风",
    "rh":19,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 13:00:00"
    },
    {
    "text":"晴",
    "temp_fc":19,
    "wind_class":"3~4级",
    "wind_dir":"西北风",
    "rh":14,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 14:00:00"
    },
    {
    "text":"晴",
    "temp_fc":19,
    "wind_class":"<3级",
    "wind_dir":"西风",
    "rh":17,
    "prec_1h":0,
    "clouds":0,
    "data_time":"2020-04-02 15:00:00"
    }
    ]
    },
    "message":"success"
    }
 */
