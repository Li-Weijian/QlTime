package com.lovezz.entity;

import java.util.ArrayList;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author liweijian123
 * @since 2019-11-03
 */
@TableName("tb_tops")
@Data
@ToString
public class TbTops implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String content;
//    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @TableField("createTime")
    private Date createTime = new Date();
    /**
     * 赞数
     */
    private Integer zan = 0;
    @TableField("userId")
    private Integer userId;

    @TableField("isDelete")
    private String isDelete;

    @TableField(exist = false)
    private String realname;

    @TableField(exist = false)
    private String nickName;

    @TableField(exist = false)
    private String avatarUrl;
}
