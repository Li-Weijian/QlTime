package com.qltime.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qltime.constant.GalleryFlagEnum;
import com.qltime.constant.MsgCommon;
import com.qltime.dto.BaseResult;
import com.qltime.dto.StatisticsDto;
import com.qltime.entity.TbGallery;
import com.qltime.entity.TbNote;
import com.qltime.entity.TbTops;
import com.qltime.service.StatisticsService;
import com.qltime.service.TbGalleryService;
import com.qltime.service.TbNoteService;
import com.qltime.service.TbTopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: liweijian
 * @Date: 2020/10/12 21:54
 * @Description:
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private TbNoteService noteService;

    @Autowired
    private TbTopsService topsService;

    @Autowired
    private TbGalleryService galleryService;


    @Override
    public BaseResult selectAllCount(List<Integer> ids) {
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setNoteCount(noteService.selectCount(new EntityWrapper<TbNote>().eq("isDelete", 0).in("userId", ids)));
        statisticsDto.setTopsCount(topsService.selectCount(new EntityWrapper<TbTops>().eq("isDelete", 0).in("userId", ids)));
        statisticsDto.setMemoryCount(galleryService.selectCount(new EntityWrapper<TbGallery>()
                .eq("flag", GalleryFlagEnum.GALLERY.getType()).in("userId", ids)));

        return BaseResult.success(MsgCommon.SUCCESS.getMessage(), statisticsDto);
    }
}
