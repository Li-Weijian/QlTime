package com.lovezz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.entity.TbUser;
import com.lovezz.job.SendSmsJob;
import com.lovezz.mapper.TbUserMapper;
import com.lovezz.service.TbUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

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


}
