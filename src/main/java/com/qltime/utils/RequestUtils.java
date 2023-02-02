package com.qltime.utils;

import com.qltime.constant.SystemConstants;
import com.qltime.model.entity.TbUser;
import com.qltime.service.components.CacheUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author liweijian
 * @Date: 2019/11/1 23:05
 * @Description:
 */
public class RequestUtils {

    private static ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    private static HttpServletRequest request = attributes.getRequest();


    /**
     * 获取当前登录用户
     *
     * @return: 登录用户
     * @auther: liweijian
     * @date: 2019/11/1 23:08
     */
    public static TbUser getLoginUser() {

        return (TbUser) request.getSession().getAttribute(SystemConstants.SESSION_USER_KEY);
    }

    /**
     * 通过token获取用户id - 适配前后端分离架构
     */
    public static Integer getLoginUserId() {
        return getUserId(UserType.OWNER);
    }

    public static Integer getHalfId() {
        return getUserId(UserType.HALF);
    }

    private static Integer getUserId(UserType userType) {

        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies).filter(cookie -> SystemConstants.TOKEN.equalsIgnoreCase(cookie.getName()))
            .map(Cookie::getValue)
            .map(token -> {
                String[] tokenArr = token.split("\\|");
                if (UserType.OWNER.equals(userType)) {
                    return Integer.parseInt(tokenArr[userType.getIndex()]);
                } else if (UserType.HALF.equals(userType) && tokenArr.length > 2) {
                    return Integer.parseInt(tokenArr[userType.getIndex()]);
                }
                return null;
            })
            .findFirst()
            .orElse(null);
    }


    /**
     * 生成token
     *
     * @param user
     */
    public String generateToken(TbUser user) {
        String token = user.getId() + "|" + user.getHelfId() + "|" + DigestUtils.sha1Hex(user.getId() + user.getUsername());
        CacheUtil.saveHalfId(user.getHelfId(), user.getId());
        return token;
    }

    /**
     * 用户类型
     */
    enum UserType {
        /**
         * 用户自己
         */
        OWNER(0),

        /**
         * 用户另一半
         */
        HALF(1),

        ;

        UserType(Integer index) {
            this.index = index;
        }

        /**
         * token数组 索引
         */
        private final Integer index;

        public Integer getIndex() {
            return index;
        }
    }
}
