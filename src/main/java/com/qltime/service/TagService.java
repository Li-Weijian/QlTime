package com.qltime.service;

import com.qltime.constant.TagType;
import com.qltime.model.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
public interface TagService extends IService<Tag> {

    /**
     * 保存并获取标签列表
     * 此方法会自动根据标签名称计算出数据库不存在的标签并写入
     * @param tagNameList
     * @return
     */
    List<Tag> saveAndGet(List<String> tagNameList);

    /**
     * 获取已存在的标签实体
     * @param tagNameList
     * @return
     */
    List<Tag> getExistTagList(Collection<String> tagNameList);
}
