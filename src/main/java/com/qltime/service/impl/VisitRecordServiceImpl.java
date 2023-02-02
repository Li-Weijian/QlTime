package com.qltime.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qltime.annotation.StartDataScope;
import com.qltime.constant.GalleryFlagEnum;
import com.qltime.constant.TagType;
import com.qltime.model.dto.VisitRecordDTO;
import com.qltime.model.entity.TagBusiness;
import com.qltime.model.entity.TbGallery;
import com.qltime.model.entity.VisitRecord;
import com.qltime.mapper.VisitRecordMapper;
import com.qltime.model.param.SaveGalleryParam;
import com.qltime.model.param.SaveVisitRecordParam;
import com.qltime.service.TagBusinessService;
import com.qltime.service.TbGalleryService;
import com.qltime.service.TbUserService;
import com.qltime.service.VisitRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qltime.utils.RequestUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * <p>
 * 探店记录 服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
@Service
public class VisitRecordServiceImpl extends ServiceImpl<VisitRecordMapper, VisitRecord> implements VisitRecordService {

    private final TagBusinessService tagBusinessService;
    private final TbGalleryService galleryService;
    private final TbUserService userService;

    public VisitRecordServiceImpl(TagBusinessService tagBusinessService, TbGalleryService galleryService, TbUserService userService) {
        this.tagBusinessService = tagBusinessService;
        this.galleryService = galleryService;
        this.userService = userService;
    }

    @Override
    public void save(SaveVisitRecordParam saveVisitRecordParam) {

        // 构建探店记录
        VisitRecord visitRecord = makeVisitRecord(saveVisitRecordParam);
        save(visitRecord);

        // 处理标签中间表
        tagBusinessService.save(saveVisitRecordParam.getTagList(), visitRecord.getId(), TagType.VISIT_RECORD);

        // 添加图片列表
        galleryService.saveGallery(SaveGalleryParam.builder()
            .flag(GalleryFlagEnum.VISIT_RECORD)
            .topsId(visitRecord.getId())
            .urls(saveVisitRecordParam.getImageList())
            .build()
        );
    }

    /**
     * 获取分页列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    @StartDataScope
    public IPage<VisitRecordDTO> page(Integer pageNo, Integer pageSize) {

        Page<VisitRecord> page = page(new Page<>(pageNo, pageSize),
            new LambdaQueryWrapper<VisitRecord>()
                .eq(VisitRecord::getUserId, RequestUtils.getLoginUserId())
                .eq(VisitRecord::getIsDelete, 0)
                .orderBy(false, false, VisitRecord::getCreated)
        );

        return new Page<VisitRecordDTO>(page.getCurrent(), page.getSize(), page.getTotal())
            .setRecords(page.getRecords()
                .stream()
                .map(this::buildVisitRecordDTO)
                .collect(Collectors.toList())
            );
    }

    @NotNull
    private VisitRecordDTO buildVisitRecordDTO(VisitRecord visitRecord) {
        return VisitRecordDTO.of(visitRecord, (e, v) -> {
                v.setImageList(galleryService.listByTopsIdAndFlag(visitRecord.getId(), GalleryFlagEnum.VISIT_RECORD).
                    stream().
                    map(TbGallery::getUrl).
                    collect(Collectors.toList()));
                v.setUser(userService.getUserById(visitRecord.getUserId()));
                v.setTagList(tagBusinessService.getTagListByTypeAndBusinessId(TagType.VISIT_RECORD, visitRecord.getId()));
            }
        );
    }

    @Override
    public VisitRecordDTO detail(String recordId) {
        return buildVisitRecordDTO(getById(recordId));
    }

    @Override
    public void delete(String recordId) {

        // 删除标签中间表
        tagBusinessService.remove(recordId);

        // 删除图片列表
        galleryService.remove(recordId);

        // TODO 删除评论列表

        // 删除记录
        removeById(recordId);
    }

    @Override
    public void incrCommentNumber(String recordId) {
        update(new LambdaUpdateWrapper<VisitRecord>()
            .eq(VisitRecord::getId, recordId)
            .setSql("`comment` = `comment` + 1")
        );
    }


    private VisitRecord makeVisitRecord(SaveVisitRecordParam saveVisitRecordParam) {
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setTitle(saveVisitRecordParam.getTitle());
        visitRecord.setContent(saveVisitRecordParam.getContent());
        visitRecord.setCreated(new Date());
        visitRecord.setUserId(RequestUtils.getLoginUserId());
        return visitRecord;
    }
}
