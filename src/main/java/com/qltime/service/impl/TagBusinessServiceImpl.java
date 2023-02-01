package com.qltime.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qltime.mapper.TagBusinessMapper;
import com.qltime.model.entity.TagBusiness;
import com.qltime.service.TagBusinessService;
import com.qltime.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void save(List<String> tagList) {
        tagList.parallelStream().forEach(tagName -> {
        });
    }
}
