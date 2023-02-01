package com.qltime.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qltime.model.entity.TbTops;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author liweijian123
 * @since 2019-11-03
 */
public interface TbTopsMapper extends BaseMapper<TbTops> {

    List<TbTops> selectTopList(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("ids") List<Integer> ids);
}
