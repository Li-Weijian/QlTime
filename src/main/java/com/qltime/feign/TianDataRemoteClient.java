package com.qltime.feign;

import com.qltime.model.dto.tianxin.TianRainbow;
import com.qltime.model.dto.tianxin.TianEnsentence;
import com.qltime.model.dto.tianxin.TianStar;
import com.qltime.model.param.TianStarParam;
import com.qltime.model.param.TianXinParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 第三方接口天行数据
 *
 * @author liweijian
 */
@FeignClient(value = "TianDataRemoteClient", url = "${application.tianxin.server}")
public interface TianDataRemoteClient {

    /**
     * 获取彩虹屁
     */
    @GetMapping(value = "/caihongpi/index",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    TianRainbow queryRainbow(@SpringQueryMap TianXinParam tianXinParam);

    /**
     * 获取优美的句子
     */
    @GetMapping(value = "/everyday/index",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    TianEnsentence queryEnsentence(@SpringQueryMap TianXinParam tianXinParam);


    /**
     * 获取星座运势
     */
    @GetMapping(value = "/star/index",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    TianStar queryStar(@SpringQueryMap TianXinParam param);


}
