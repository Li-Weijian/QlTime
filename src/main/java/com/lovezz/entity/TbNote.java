package com.lovezz.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-09
 */

@TableName("tb_note")
public class TbNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 内容
     */
    private String context;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date date;
    /**
     * 是否删除 0未删除 1已删除
     */
    @TableField("isDelete")
    private String isDelete;
    /**
     * 是否完成 0未完成 1已完成
     */
    @TableField("isComplete")
    private String isComplete;

    @TableField("userId")
    private Integer userId;

    @TableField(exist = false)
    private String realname;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    @Override
    public String toString() {
        return "TbNote{" +
        ", id=" + id +
        ", context=" + context +
        ", date=" + date +
        ", isDelete=" + isDelete +
        ", isComplete=" + isComplete +
        "}";
    }
}
