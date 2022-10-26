package com.qltime.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-30
 */
@TableName("tb_user")
@NoArgsConstructor
@ToString
@Data
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 密码
     */
    private String password;
    /**
     * 注册手机
     */
    private String phone;
    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, strategy = FieldStrategy.IGNORED)
    private Date created;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE, strategy = FieldStrategy.IGNORED)
    private Date updated;
    /**
     * 上线时间
     */
    @TableField("onlieTime")
    private Date onlieTime;
    /**
     * 是否删除 0：未删除， 1：已删除
     */
    @TableField("isDelete")
    private Integer isDelete;

    /**
     * openId
     */
    private String openId;

    /**
     * sessionKey
     */
    private String sessionKey;

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

    private String code;

    private String sceneCode;

    /**
     * 另一半id
     */
    private Integer helfId;

    /**
     * 在一起的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date togetheTime;

    @TableField(exist = false)
    private String token;



}
