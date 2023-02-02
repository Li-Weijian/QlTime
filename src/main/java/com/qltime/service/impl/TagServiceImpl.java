package com.qltime.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qltime.model.entity.Tag;
import com.qltime.mapper.TagMapper;
import com.qltime.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * 保存并获取标签列表
     * 此方法会自动根据标签名称计算出数据库不存在的标签并写入
     *
     * @param tagNameList
     * @return
     */
    @Override
    public List<Tag> saveAndGet(List<String> tagNameList) {

        List<Tag> existTagList = getExistTagList(tagNameList);

        // 求出差集，差集就是在标签数据库中不存在的，需要新增
        Collection<String> tagDisjunction = CollUtil.disjunction(existTagList.stream().map(Tag::getTagName).collect(Collectors.toList()), tagNameList);
        saveBatch(tagDisjunction.stream().map(Tag::new).collect(Collectors.toList()));

        // 拼接返回
        return CollUtil.addAllIfNotContains(existTagList, getExistTagList(tagDisjunction));
    }

    @Override
    public List<Tag> getExistTagList(Collection<String> tagNameList) {
        if (CollUtil.isEmpty(tagNameList)){
            return CollUtil.newArrayList();
        }

        return list(new LambdaQueryWrapper<Tag>()
            .in(Tag::getTagName, tagNameList)
            .eq(Tag::getIsDelete, 0));
    }
}
