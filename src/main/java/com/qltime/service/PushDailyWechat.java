package com.qltime.service;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @author liweijian
 * @date 2022/12/29 16:22
 */
public interface PushDailyWechat {
    void pushWechat() throws WxErrorException;
}
