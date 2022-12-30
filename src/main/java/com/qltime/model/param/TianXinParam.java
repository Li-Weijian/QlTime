package com.qltime.model.param;

import lombok.Builder;
import lombok.Data;

/**
 * 天行请求参数
 * @author liweijian
 * @date 2022/12/29 16:30
 */
@Builder
@Data
public class TianXinParam {

    /**
     * APIKEY
     */
    private String key;
}
