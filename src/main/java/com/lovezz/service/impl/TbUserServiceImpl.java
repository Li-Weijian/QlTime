package com.lovezz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.entity.TbUser;
import com.lovezz.mapper.TbUserMapper;
import com.lovezz.service.TbUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

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

    @Override
    public TbUser login(String username, String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return null;
        }

        String pwMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        List<TbUser> userList = userMapper.selectList(new EntityWrapper<TbUser>().eq("password", pwMd5).eq("isDelete", 0)
                .andNew().eq("phone",username).or().eq("email",username).or().eq("username", username));

//                .andNew("phone",username).or("email",username).or("username", username));

        if (userList != null&&userList.size() > 0){
            return userList.get(0);
        }

        return null;
    }
}
