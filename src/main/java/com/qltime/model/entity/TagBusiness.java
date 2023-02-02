package com.qltime.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.qltime.constant.TagType;
import lombok.Data;

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
    private TagType type;
    /**
     * 标签ID
     */
    @TableField("tagId")
    private String tagId;
}
