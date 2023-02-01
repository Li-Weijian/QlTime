package com.qltime.model.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author liweijian123
 * @since 2020-01-01
 */
@TableName("tb_comments")
@Data
@ToString
public class TbComments implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String content;
    /**
     * 回复用户
     */
    @TableField("userId")
    private Integer userId;
    /**
     * 留言id
     */
    @TableField("topId")
    private String topId;
    /**
     * 是否删除： 0-未删除 1-已删除
     */
    @TableField("isDelete")
    private String isDelete;
    private Date created = new Date();
    /**
     * 被回复用户
     */
    @TableField("replayUserId")
    private Integer replayUserId;
    /**
     * 上一条回复
     */
    @TableField("lastId")
    private String lastId;

    @TableField("flag")
    private String flag;

    @TableField(exist = false)
    private String nickName;

    @TableField(exist = false)
    private String avatarUrl;

}
