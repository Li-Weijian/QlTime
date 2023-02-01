package com.qltime.model.entity;

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
public class TagBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 业务ID
     */
    @TableField("businessId")
    private String businessId;
    /**
     * 业务类型
     */
    private String type;
    /**
     * 标签ID
     */
    @TableField("tagId")
    private String tagId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "TagBusiness{" +
        ", id=" + id +
        ", businessId=" + businessId +
        ", type=" + type +
        ", tagId=" + tagId +
        "}";
    }
}
