package com.qltime.model.dto.wxpush;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liweijian
 * @date 2023/1/8 19:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseWxPush {

    private String wechatId;
    private String templateId;
}
