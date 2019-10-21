package com.lovezz.service;

import com.lovezz.entity.TbLovetext;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-19
 */
public interface TbLovetextService extends IService<TbLovetext> {

    /**
     * 随机获取一句话
     *
     * @return:
     * @auther: liweijian
     * @date: 2019/10/21 20:42
     */
    public String getOneTextRandom();

}
