package com.qltime.model.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
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
    private String type;
    private Date created;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getReplayUserId() {
        return replayUserId;
    }

    public void setReplayUserId(Integer replayUserId) {
        this.replayUserId = replayUserId;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "VisitRecordComment{" +
        ", id=" + id +
        ", content=" + content +
        ", userId=" + userId +
        ", recordId=" + recordId +
        ", isDelete=" + isDelete +
        ", replayUserId=" + replayUserId +
        ", lastId=" + lastId +
        ", type=" + type +
        ", created=" + created +
        "}";
    }
}
