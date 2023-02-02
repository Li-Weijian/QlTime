package com.qltime.model.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.qltime.constant.CommentType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
@Data
public class VisitRecordComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 内容
     */
    private String content;

    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 记录ID
     */
    private String recordId;

    @TableLogic
    @TableField("isDelete")
    private String isDelete;
    /**
     * 被回复用户ID
     */
    @TableField("replayUserId")
    private Integer replayUserId;

    /**
     * 上层ID
     */
    @TableField("lastId")
    private String lastId;
    /**
     * 类型 0：评论 1：回复
     */
    private CommentType type;

    private Date created;

}
