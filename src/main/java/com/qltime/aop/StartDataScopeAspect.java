package com.qltime.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.qltime.annotation.OperationNotice;
import com.qltime.constant.NoticeType;
import com.qltime.constant.WechatTemplateType;
import com.qltime.utils.DataScopeHelper;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 开启数据权限
 *
 * @author liweijian
 */
@Aspect
@Component
@Slf4j
public class StartDataScopeAspect {

    /**
     * 切入点，切的是注解，也可以使用包名进行切入
     * '@Pointcut("execution(* com.qltime.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(com.qltime.annotation.StartDataScope)")
    public void startDataScope() {
    }


    @After("startDataScope()")
    public void interceptor(JoinPoint joinPoint) throws Throwable {
        DataScopeHelper.startDataScope();
    }

}
