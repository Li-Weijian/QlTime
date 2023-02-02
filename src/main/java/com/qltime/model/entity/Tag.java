package com.qltime.model.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 标签名称
     */
    @TableField("tagName")
    private String tagName;

    @TableField(fill = FieldFill.INSERT)
    private Date created;

    /**
     * 标签颜色
     */
    private String color;

    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
