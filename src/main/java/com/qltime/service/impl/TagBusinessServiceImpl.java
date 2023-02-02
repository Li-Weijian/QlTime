package com.qltime.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qltime.constant.TagType;
import com.qltime.mapper.TagBusinessMapper;
import com.qltime.model.entity.Tag;
import com.qltime.model.entity.TagBusiness;
import com.qltime.service.TagBusinessService;
import com.qltime.service.TagService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
@Service
public class TagBusinessServiceImpl extends ServiceImpl<TagBusinessMapper, TagBusiness> implements TagBusinessService {

    private final TagService tagService;

    public TagBusinessServiceImpl(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public void save(List<String> tagList, String businessId, TagType type) {
        // 获取标签并构建中间表
        List<TagBusiness> tagBusinessList = tagService.saveAndGet(tagList).stream().map(tag -> {
            TagBusiness tagBusiness = new TagBusiness();
            tagBusiness.setTagId(tag.getId());
            tagBusiness.setBusinessId(businessId);
            tagBusiness.setType(type);
            return tagBusiness;
        }).collect(Collectors.toList());

        // 获取最终的集合，写入中间表
        saveBatch(tagBusinessList);
    }

    @Override
    public List<Tag> getTagListByTypeAndBusinessId(TagType type, String businessId) {
        List<String> tagIds = getTagIds(type, businessId);
        if (CollUtil.isEmpty(tagIds)){
            return ListUtil.empty();
        }
        return tagService.listByIds(tagIds);
    }

    @Override
    public void remove(String businessId) {
        remove(new LambdaQueryWrapper<TagBusiness>()
            .eq(TagBusiness::getType, TagType.VISIT_RECORD)
            .eq(TagBusiness::getBusinessId, businessId)
        );
    }

    private List<String> getTagIds(TagType type, String businessId) {
        return list(new LambdaQueryWrapper<TagBusiness>()
            .eq(TagBusiness::getType, type)
            .eq(TagBusiness::getBusinessId, businessId)
            .select(TagBusiness::getTagId)
        ).stream().map(TagBusiness::getTagId).collect(Collectors.toList());
    }
}
