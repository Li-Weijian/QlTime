package com.qltime.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

/**
 * <p>
 * 探店记录
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
public class VisitRecord implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Integer like;
    /**
     * 评论数
     */
    private Integer comment;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "VisitRecord{" +
        ", id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", created=" + created +
        ", userId=" + userId +
        ", isDelete=" + isDelete +
        ", like=" + like +
        ", comment=" + comment +
        "}";
    }
}
