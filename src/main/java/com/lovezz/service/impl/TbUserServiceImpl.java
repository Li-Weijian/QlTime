package com.lovezz.service.impl;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.dto.RawDataDto;
import com.lovezz.dto.WxLoginInfoDto;
import com.lovezz.entity.TbUser;
import com.lovezz.job.SendSmsJob;
import com.lovezz.mapper.TbUserMapper;
import com.lovezz.service.TbUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lovezz.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-30
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

    @Autowired
    private TbUserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(TbUserServiceImpl.class);

    @Override
    public TbUser login(String username, String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return null;
        }

        String pwMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        List<TbUser> userList = userMapper.selectList(new EntityWrapper<TbUser>().eq("password", pwMd5).eq("isDelete", 0)
                .andNew().eq("phone",username).or().eq("email",username).or().eq("username", username));


        if (userList != null && userList.size() > 0){
            TbUser user = userList.get(0);
            this.setUserOnline(user.getId());
            logger.info("用户:"+user.getUsername() + " 登录成功，登录时间:"+ new Date());
            return user;
        }

        return null;
    }

    @Override
    public void setUserOnline(Integer userid) {
        TbUser user = userMapper.selectById(userid);
        user.setOnlieTime(new Date());
        userMapper.updateById(user);
    }

    @Override
    public List<String> selectUserEmail(Map param) {
        return userMapper.selectUserEmail(param);
    }

    @Override
    public String selectUserName(Integer userid) {
        return userMapper.selectById(userid).getRealname();
    }

    @Override
    public TbUser addOrUpdateUser(WxLoginInfoDto wxLoginInfo) {
        TbUser user = selectOne(new EntityWrapper<TbUser>().eq("openId", wxLoginInfo.getOpenId()));
        if (null == user){
            // 新增
            user = makeUserInfo(wxLoginInfo);
        }else {
            // 更新
            user.setOnlieTime(new Date());
            user.setUpdated(new Date());
            updateById(user);
        }
        return setSensitiveInfoToNull(user);
    }

    @Override
    public Cookie makeCookieByToken(String token) {
        Cookie cookie = new Cookie("token", token);
        //不设置路径的话, 会以当前路径为默认值，会导致其他页面不携带该cookie
        cookie.setPath("/");
        cookie.setMaxAge(2147483647);
        return cookie;
    }

    /**
     * 构造用户数据
     * @param wxLoginInfo
     * @return
     */
    private TbUser makeUserInfo(WxLoginInfoDto wxLoginInfo) {
        TbUser user = new TbUser();
        BeanUtils.copyProperties(wxLoginInfo, user);
        String rawData = wxLoginInfo.getRawData();
        RawDataDto info = JSON.parseObject(rawData, RawDataDto.class);
        BeanUtils.copyProperties(info, user);
        user.setUsername(user.getNickName());
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        user.setRealname(user.getNickName());
        user.setCreated(new Date());
        insert(user);
        return user;
    }

    /**
     * 将敏感信息置空
     * @param user
     * @return
     */
    private TbUser setSensitiveInfoToNull(TbUser user) {
        user.setSessionKey(null);
        user.setPassword(null);
        return user;
    }


}
