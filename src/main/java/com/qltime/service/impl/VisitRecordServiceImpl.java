package com.qltime.service.impl;

import com.qltime.constant.GalleryFlagEnum;
import com.qltime.model.entity.VisitRecord;
import com.qltime.mapper.VisitRecordMapper;
import com.qltime.model.param.SaveGalleryParam;
import com.qltime.model.param.SaveVisitRecordParam;
import com.qltime.service.TagBusinessService;
import com.qltime.service.TbGalleryService;
import com.qltime.service.VisitRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qltime.utils.RequestUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public VisitRecordServiceImpl(TagBusinessService tagBusinessService, TbGalleryService galleryService) {
        this.tagBusinessService = tagBusinessService;
        this.galleryService = galleryService;
    }

    @Override
    public void save(SaveVisitRecordParam saveVisitRecordParam) {

        // 构建探店记录
        VisitRecord visitRecord = makeVisitRecord(saveVisitRecordParam);
        save(visitRecord);

        // 处理标签中间表
        tagBusinessService.save(saveVisitRecordParam.getTagList());
        // 添加图片列表
        galleryService.saveGallery(SaveGalleryParam.builder()
            .flag(GalleryFlagEnum.VISIT_RECORD)
            .topsId(visitRecord.getId())
            .urls(saveVisitRecordParam.getImageList())
            .build()
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
