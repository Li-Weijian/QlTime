package com.qltime.model.dto;

import com.qltime.model.entity.TbUser;
import lombok.Data;

/**
 * @auther: liweijian
 * @Date: 2020/9/23 09:07
 * @Description: 情侣相关的信息
 */
@Data
public class LoversDto {

    /**
     * 当前用户
     */
    private TbUser myself;

    /**
     * 另一半
     */
    private TbUser helf;

    /**
     * 在一起的天数
     */
    private Long day;
}
