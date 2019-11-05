package com.lovezz.mapper;

import com.lovezz.entity.TbTops;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liweijian123
 * @since 2019-11-03
 */
public interface TbTopsMapper extends BaseMapper<TbTops> {

    List<TbTops> selectTopList();
}
