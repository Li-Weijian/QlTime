package com.lovezz.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liweijian123
 * @since 2019-11-03
 */
@TableName("tb_comments")
public class TbComments implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String ccontent;
    /**
     * 评论等级 0- 评论说说 1- 评论评论
     */
    private String state;
    /**
     * 评论的评论id
     */
    private String cid;
    @TableField("userId")
    private Integer userId;
    /**
     * 留言id
     */
    @TableField("topId")
    private String topId;

    @TableField("isDelete")
    private String isDelete;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    @Override
    public String toString() {
        return "TbComments{" +
        ", id=" + id +
        ", ccontent=" + ccontent +
        ", state=" + state +
        ", cid=" + cid +
        ", userId=" + userId +
        ", topId=" + topId +
        "}";
    }
}
