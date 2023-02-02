package com.qltime.annotation;

import com.qltime.constant.NoticeType;
import com.qltime.constant.OperationModule;

import java.lang.annotation.*;

/**
 * 开启数据权限，查询时自动拼接 OR userId = {halfId}
 * @author liweijian
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface StartDataScope {


}
