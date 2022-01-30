package com.lovezz.aop;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.annotation.OperationEmailDetail;
import com.lovezz.entity.TbUser;
import com.lovezz.service.MailService;
import com.lovezz.service.TbUserService;
import com.lovezz.utils.RequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 邮件通知切面
 * @Auther: liweijian
 * @Date: 2019/12/9 20:23
 * @Description:
 */

@Aspect
@Component
public class NoticeAspect {

    @Autowired
    private MailService mailService;
    @Autowired
    private TbUserService userService;

    /**
     * 切入点，切的是注解，也可以使用包名进行切入
     * '@Pointcut("execution(* com.lovezz.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(com.lovezz.annotation.OperationEmailDetail)")
    public void operationEmail(){}


    @After("operationEmail()")
    public void Interceptor(JoinPoint joinPoint) throws Throwable {
        sendMail(joinPoint);
    }

    private void sendMail(JoinPoint joinPoint){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationEmailDetail annotation = signature.getMethod().getAnnotation(OperationEmailDetail.class);

        if (annotation != null){

            //内容
            String content = annotation.content();
            //操作模块
            String operationClass = annotation.operationClass();
            //邮件主题
            String subject = annotation.subject();

            //传入的发送列表为空，从数据库中查询
            List<String> emailList = CollUtil.newArrayList(annotation.to());
            if (emailList.size() == 0){
                Optional.ofNullable(userService.getHalf(new RequestUtils().getLoginUserId())).ifPresent(user -> {
                    emailList.add(user.getEmail());
                });
            }

            for (String email: emailList) {
                content = content + "\r\n发布时间：" + LocalDateTime.now();
                mailService.sendSimpleMail(email, "【新消息通知】" + operationClass, content);
            }
        }
    }

}
