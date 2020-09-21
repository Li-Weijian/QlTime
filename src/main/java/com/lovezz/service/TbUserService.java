package com.lovezz.service;

import com.lovezz.dto.WxLoginInfoDto;
import com.lovezz.entity.TbUser;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Map;

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

    /**
     *
     * 设置上线时间
     * @param:
     * @auther: liweijian
     * @date: 2019/11/19 18:17
     */
    void setUserOnline(Integer userid);

    /**
     * 获取用户邮箱
     * @auther: liweijian
     * @date: 2019/12/9 22:24
     */
    List<String> selectUserEmail(Map param);

    /**
     * 获取用户名
     * @auther: liweijian
     * @date: 2020/1/1 20:02
     */
    String selectUserName(Integer userid);

    /**
     * 添加或者更新用户
     * @param wxLoginInfo
     * @return
     */
    TbUser addOrUpdateUser(WxLoginInfoDto wxLoginInfo);

    /**
     * 为token构造cookie
     * @return
     */
    Cookie makeCookieByToken(String token);

}
