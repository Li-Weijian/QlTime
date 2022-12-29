package com.qltime.mapper;

import com.qltime.entity.TbNote;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-09
 */


public interface TbNoteMapper extends BaseMapper<TbNote> {

    List<TbNote> selectNoteListAndUser(@Param("offset") int offset, @Param("limit") int limit, @Param("ids") List<Integer> ids);

}
