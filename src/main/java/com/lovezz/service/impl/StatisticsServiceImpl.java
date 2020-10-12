package com.lovezz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.constant.GalleryFlagEnum;
import com.lovezz.constant.MsgCommon;
import com.lovezz.dto.BaseResult;
import com.lovezz.dto.StatisticsDto;
import com.lovezz.entity.TbGallery;
import com.lovezz.entity.TbNote;
import com.lovezz.entity.TbTops;
import com.lovezz.service.StatisticsService;
import com.lovezz.service.TbGalleryService;
import com.lovezz.service.TbNoteService;
import com.lovezz.service.TbTopsService;
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
