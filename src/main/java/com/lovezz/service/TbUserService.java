package com.lovezz.service;

import com.lovezz.entity.TbUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-30
 */
public interface TbUserService extends IService<TbUser> {

    TbUser login(String username, String password);
}
