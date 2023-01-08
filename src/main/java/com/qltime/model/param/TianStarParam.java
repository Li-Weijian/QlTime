package com.qltime.model.param;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author liweijian
 * @date 2023/1/8 18:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TianStarParam extends TianXinParam{

    /**
     * 星座中文或英文名
     */
    private String astro;
}
