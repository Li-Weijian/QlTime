package com.qltime.service;

import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liweijian
 * @date 2022/12/29 16:22
 */
public interface PushDailyWechat {
    /**
     * 推送数据
     */
    void pushWechat();

}
