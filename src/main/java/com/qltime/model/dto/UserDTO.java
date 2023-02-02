package com.qltime.model.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qltime.model.entity.TbUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liweijian
 * @date 2023/2/2 17:36
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String realname;

    /**
     * 注册手机
     */
    private String phone;
    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 上线时间
     */
    private Date onlieTime;


    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickName;


    /**
     * 性别 0女 1男
     */
    private Integer gender;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 国家
     */
    private String country;


    /**
     * 另一半id
     */
    private Integer helfId;

    /**
     * 在一起的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date togetheTime;


    /**
     * 星座
     */
    private String star;

    public static UserDTO of(TbUser user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
