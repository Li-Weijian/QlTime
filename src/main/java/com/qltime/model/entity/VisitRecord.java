package com.qltime.model.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 探店记录
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
@Data
public class VisitRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表时间
     */
    private Date created;
    /**
     * 发表用户
     */
    @TableField("userId")
    private Integer userId;
    /**
     * 是否删除
     */
    @TableField("isDelete")
    private Integer isDelete;
    /**
     * 赞
     */
    private Integer likeNum;
    /**
     * 评论数
     */
    private Integer comment;


}
