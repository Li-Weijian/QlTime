package com.lovezz.entity;

import java.util.Date;
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
@TableName("tb_tops")
public class TbTops implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String content;
    @TableField("createTime")
    private Date createTime = new Date();
    /**
     * 赞数
     */
    private Integer zan;
    @TableField("userId")
    private Integer userId;


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getZan() {
        return zan;
    }

    public void setZan(Integer zan) {
        this.zan = zan;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TbTops{" +
        ", id=" + id +
        ", content=" + content +
        ", createTime=" + createTime +
        ", zan=" + zan +
        ", userId=" + userId +
        "}";
    }
}
