package com.qltime.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.qltime.annotation.OperationNotice;
import com.qltime.config.WxConfig;
import com.qltime.constant.NoticeType;
import com.qltime.constant.WechatTemplateType;
import com.qltime.service.MailService;
import com.qltime.service.TbUserService;
import com.qltime.service.WxPushService;
import com.qltime.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 邮件通知切面
 *
 * @author liweijian
 * @Date: 2019/12/9 20:23
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class NoticeAspect {

    private final MailService mailService;
    private final TbUserService userService;
    private final WxPushService wxPushService;
    private final WxConfig wxConfig;

    public NoticeAspect(MailService mailService, TbUserService userService, WxPushService wxPushService, WxConfig wxConfig) {
        this.mailService = mailService;
        this.userService = userService;
        this.wxPushService = wxPushService;
        this.wxConfig = wxConfig;
    }

    /**
     * 切入点，切的是注解，也可以使用包名进行切入
     * '@Pointcut("execution(* com.qltime.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(com.qltime.annotation.OperationNotice)")
    public void operationNotice() {
    }


    @After("operationNotice()")
    public void interceptor(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationNotice annotation = signature.getMethod().getAnnotation(OperationNotice.class);

        try {
            if (annotation != null) {
                if (NoticeType.WX_PUSH.equals(annotation.noticeType())) {
                    sendWxPush(annotation);
                } else if (NoticeType.EMAIL.equals(annotation.noticeType())) {
                    sendMail(annotation);
                }
            }
        } catch (Exception e) {
            log.error("推送消息失败. 推送类型: {}, 推送模块: {} ", annotation.noticeType(), annotation.operationClass(), e);
        }
    }

    /**
     * 推送微信
     *
     * @param annotation
     */
    private void sendWxPush(OperationNotice annotation) {
        Optional.ofNullable(userService.getHalf(new RequestUtils().getLoginUserId())).ifPresent(user -> {
            WxMpTemplateMessage message = WxMpTemplateMessage.builder().build();
            message.addData(new WxMpTemplateData("user", user.getUsername()));
            message.addData(new WxMpTemplateData("module", annotation.operationClass()));
            message.addData(new WxMpTemplateData("time", LocalDateTimeUtil.format(LocalDateTime.now(),  DatePattern.NORM_DATETIME_PATTERN)));
            wxPushService.push(wxConfig.getWechatTemplateByType(WechatTemplateType.NOTICE), user.getWechatId(), message);
        });
    }


    /**
     * 推送邮件
     *
     * @param annotation
     */
    private void sendMail(OperationNotice annotation) {

        //内容
        String content = annotation.content();
        //操作模块
        String operationClass = annotation.operationClass();

        //传入的发送列表为空，从数据库中查询
        List<String> emailList = CollUtil.newArrayList(annotation.to());
        if (emailList.size() == 0) {
            Optional.ofNullable(userService.getHalf(new RequestUtils().getLoginUserId())).ifPresent(user -> {
                emailList.add(user.getEmail());
            });
        }

        for (String email : emailList) {
            content = content + "\r\n发布时间：" + LocalDateTime.now();
            mailService.sendSimpleMail(email, "【新消息通知】" + operationClass, content);
        }
    }

}
