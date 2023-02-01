package com.qltime.service;

import com.qltime.model.entity.TbNote;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-09
 */
public interface TbNoteService extends IService<TbNote> {

    /**
     * 功能描述: 获取待办列表
     *
     * @return: 待办列表
     * @auther: liweijian
     * @date: 2019/10/9 20:58
     * @param offset
     * @param limit
     */
    List<TbNote> getNoteList(int offset, int limit, List<Integer> ids);


}
