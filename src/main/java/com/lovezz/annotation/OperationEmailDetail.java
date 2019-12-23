package com.lovezz.annotation;

import com.lovezz.constant.OperationModule;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author liweijian
 * @Date: 2019/12/9 20:29
 * @Description:
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationEmailDetail {

    /**
     * 邮件需要发送的文本
     * @auther: liweijian
     * @date: 2019/12/9 20:36
     */
    String content() default "";

    /**
     * 邮件主题 默认为空，切面根据操作模块来拼接
     * ps: 【新消息通知】小约定
     * @auther: liweijian
     * @date: 2019/12/9 20:37
     */
    String subject() default "";

    /**
     * 发送邮箱列表 默认为空，切面将获取当前数据库中所有有效用户进行通知
     * @auther: liweijian
     * @date: 2019/12/9 20:45
     */
    String[] to() default {};

    /**
     * 操作模块
     * @auther: liweijian
     * @date: 2019/12/9 20:55
     */
    String operationClass() default OperationModule.UNKNOWN;

}
