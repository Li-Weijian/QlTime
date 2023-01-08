package com.qltime.model.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 天行请求参数
 * @author liweijian
 * @date 2022/12/29 16:30
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TianXinParam {

    /**
     * APIKEY
     */
    private String key;
}
