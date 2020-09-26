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
    public List<TbNote> getNoteList(int offset, int limit, List<Integer> ids) {
        if (offset <= 0){
            offset = 0;
        }

        return noteMapper.selectNoteListAndUser(offset,limit, ids);
    }


}
