package com.qltime.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qltime.model.entity.TbGallery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-12
 */
public interface TbGalleryMapper extends BaseMapper<TbGallery> {

    List<String> selectGalleryWrapper();

}
