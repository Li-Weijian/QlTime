package com.lovezz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.entity.TbNote;
import com.lovezz.mapper.TbNoteMapper;
import com.lovezz.service.TbNoteService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-09
 */
@Service
public class TbNoteServiceImpl extends ServiceImpl<TbNoteMapper, TbNote> implements TbNoteService {

    @Autowired
    private TbNoteMapper noteMapper;

    @Override
    public List<TbNote> getNoteList(int offset, int limit) {
//        return noteMapper.selectPage(new RowBounds(offset,limit),null);
        return noteMapper.selectList(new EntityWrapper<TbNote>().eq("isDelete","0").orderBy("isComplete",true));
    }


}
