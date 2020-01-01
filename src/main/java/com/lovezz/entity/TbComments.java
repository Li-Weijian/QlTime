package com.lovezz.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

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

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "TbComments{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", topId='" + topId + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", created=" + created +
                ", replayUserId=" + replayUserId +
                ", lastId='" + lastId + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
