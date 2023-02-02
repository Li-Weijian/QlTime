package com.qltime.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qltime.constant.TagType;
import com.qltime.model.entity.TagBusiness;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
public interface TagBusinessService extends IService<TagBusiness> {

    /**
     * 保存业务标签关系表
     * @param tagList tag名称
     * @param businessId 业务id
     */
    void save(List<String> tagList, String businessId, TagType type);
}
