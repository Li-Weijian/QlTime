package com.qltime.service;

import com.qltime.model.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-09
 */
public interface TestService extends IService<Test> {

    public Test getTest();
}
