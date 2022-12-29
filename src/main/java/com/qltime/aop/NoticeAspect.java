package com.qltime.aop;

import cn.hutool.core.collection.CollUtil;
import com.qltime.annotation.OperationEmailDetail;
import com.qltime.service.MailService;
import com.qltime.service.TbUserService;
import com.qltime.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
@Slf4j
public class NoticeAspect {

    @Autowired
    private MailService mailService;
    @Autowired
    private TbUserService userService;

    /**
     * 切入点，切的是注解，也可以使用包名进行切入
     * '@Pointcut("execution(* com.qltime.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(com.qltime.annotation.OperationEmailDetail)")
    public void operationEmail(){}


    @After("operationEmail()")
    public void Interceptor(JoinPoint joinPoint) throws Throwable {
        try {
            sendMail(joinPoint);
        }catch (Exception e){
            log.error("发送邮件失败. ", e);
        }
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
