package com.lovezz.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * 
 * </p>
 *
 * @author liweijian123
 * @since 2019-12-07
 */
@TableName("tb_menu")
public class TbMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id = UUID.randomUUID().toString().replace("-","");
    /**
     * 图标
     */
    private String icon;
    /**
     * 链接
     */
    private String url;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 是否删除 0：未删除，1：已删除
     */
    @TableField("isDelete")
    private String isDelete;
    private Date created = new Date();
    private Date updated = new Date();

    /**
     * 用途： 0- 前后端分离菜单， 1-单体菜单， 2-健康统计菜单
     */
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "TbMenu{" +
        ", id=" + id +
        ", icon=" + icon +
        ", url=" + url +
        ", name=" + name +
        ", isDelete=" + isDelete +
        ", created=" + created +
        ", updated=" + updated +
        "}";
    }
}
