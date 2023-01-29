package com.qltime.service;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 微信推送
 * @author liweijian
 * @date 2023/1/29 18:11
 */
public interface WxPushService {

    /**
     * 微信推送
     * @param templateId
     * @param wechatId
     * @param message
     * @return
     */
    Boolean push(String templateId, String wechatId, WxMpTemplateMessage message);

    /**
     * 微信推送， message 中包含 templateId 和 wechatId
     * @param message
     * @return
     */
    Boolean push(WxMpTemplateMessage message);
}
