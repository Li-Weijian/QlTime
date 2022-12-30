/**
  * Copyright 2022 json.cn
  */
package com.qltime.model.dto.baidu;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-12-29 18:41:36
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class BaiduResult {

    private Location location;
    private Now now;
    private List<Indexes> indexes;
    private List<Alerts> alerts;
    private List<Forecasts> forecasts;
    private List<Forecast_hours> forecast_hours;

}
