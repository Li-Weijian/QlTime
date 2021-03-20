package com.lovezz.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.constant.MsgCommon;
import com.lovezz.dto.BaseResult;
import com.lovezz.dto.LoversDto;
import com.lovezz.dto.RawDataDto;
import com.lovezz.dto.WxLoginInfoDto;
import com.lovezz.entity.TbUser;
import com.lovezz.exception.CommonException;
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
import java.util.*;

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

    @Override
    public LoversDto selectLover(Integer id) throws Exception {
        TbUser user = selectById(id);
        if (null == user){
            throw new Exception(MsgCommon.USER_NULL.getMessage());
        }
        LoversDto loversDto = new LoversDto();
        user = setSensitiveInfoToNull(user);
        loversDto.setMyself(user);

        TbUser half = selectById(user.getHelfId());
        if (half != null){
            half = setSensitiveInfoToNull(half);
            loversDto.setHelf(half);
            if (user.getTogetheTime() != null){
                loversDto.setDay(DateUtil.between(user.getTogetheTime(), new Date(), DateUnit.DAY));
            }
        }
        return loversDto;
    }

    @Override
    public void setTogetherTime(TbUser user) {
        // 更新自己
        TbUser myself = selectById(user.getId());
        myself.setTogetheTime(user.getTogetheTime());
        updateById(myself);

        //更新另一半
        TbUser helf = selectById(myself.getHelfId());
        helf.setTogetheTime(user.getTogetheTime());
        updateById(helf);
    }

    @Override
    public BaseResult setHalf(TbUser user) {
        TbUser myself = selectById(user.getId());
        TbUser helf = selectById(user.getHelfId());
        if (null == myself || null == helf){
            return BaseResult.fail(MsgCommon.USER_NULL.getStatus(), MsgCommon.USER_NULL.getMessage());
        }

        myself.setHelfId(user.getHelfId());
        helf.setHelfId(myself.getId());
        updateById(myself);
        updateById(helf);
        return MsgCommon.SUCCESS;
    }

    @Override
    public List<Integer> selectAllIds(Integer userId) throws CommonException {
        TbUser user = selectById(userId);
        if (null == user){
            throw new CommonException(MsgCommon.USER_NULL);
        }

        return Arrays.asList(user.getId(), user.getHelfId());
    }

    @Override
    public void clearRelationship() throws CommonException {
        List<Integer> allIds = this.selectAllIds(new RequestUtils().getLoginUserId());
        for (Integer id : allIds) {
            TbUser user = this.selectById(id);
            user.setId(null);
            user.setHelfId(null);
            user.setTogetheTime(null);
            this.insert(user);
        }
        this.deleteBatchIds(allIds);
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
