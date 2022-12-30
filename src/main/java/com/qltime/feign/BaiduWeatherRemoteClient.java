package com.qltime.feign;
import com.qltime.model.dto.baidu.BaiduWeatherResult;
import com.qltime.model.param.BaiduWeatherParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 第三方接口天行数据
 *
 * @author liweijian
 * @date 2022/12/29 18:23
 */
@FeignClient(value = "BaiduWeatherRemoteClient", url = "${application.baidu.server}")
public interface BaiduWeatherRemoteClient {

    /**
     * 查询天气
     * @param baiduWeatherParam
     * @return
     */
    @GetMapping(value = "weather/v1/",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    BaiduWeatherResult queryWeather(@SpringQueryMap BaiduWeatherParam baiduWeatherParam);

}
