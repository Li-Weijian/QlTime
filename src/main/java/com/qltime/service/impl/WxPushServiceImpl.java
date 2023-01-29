package com.qltime.service.impl;

import com.alibaba.fastjson.JSON;
import com.qltime.config.WxConfig;
import com.qltime.service.WxPushService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author liweijian
 * @date 2023/1/29 18:17
 */
@Slf4j
@Service
public class WxPushServiceImpl implements WxPushService {

    /**
     * 微信配置类
     **/
    private final WxConfig wxConfig;

    public WxPushServiceImpl(WxConfig wxConfig) {
        this.wxConfig = wxConfig;
    }


    /**
     * 微信推送
     *
     * @param templateId
     * @param wechatId
     * @param message
     * @return
     */
    @Override
    public Boolean push(String templateId, String wechatId, WxMpTemplateMessage message) {
        message.setTemplateId(templateId);
        message.setToUser(wechatId);
        return push(message);
    }

    /**
     * 微信推送， message 中包含 templateId 和 wechatId
     *
     * @param message
     * @return
     */
    @Override
    public Boolean push(WxMpTemplateMessage message) {
        WxMpService wxMpService = getWxMpService();
        String msgId = null;
        try {
            msgId = wxMpService.getTemplateMsgService().sendTemplateMsg(message);
            log.info("发送的消息为：{}", JSON.toJSONString(message));
        } catch (WxErrorException e) {
            log.error("[微信推送失败]. wechatId: {}, templateId: {}", message.getToUser(), message.getTemplateId(), e);
        }
        return !StringUtils.isEmpty(msgId);
    }


    private WxMpService getWxMpService() {

        // 创建配置信息
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();

        wxStorage.setAppId(wxConfig.getWxPush().getAppid());
        wxStorage.setSecret(wxConfig.getWxPush().getAppSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        return wxMpService;
    }


}
