package com.qltime.service.impl;

import com.qltime.model.dto.BaseResult;
import com.qltime.model.entity.TbComments;
import com.qltime.mapper.TbCommentsMapper;
import com.qltime.service.TbCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2019-11-03
 */
@Service
public class TbCommentsServiceImpl extends ServiceImpl<TbCommentsMapper, TbComments> implements TbCommentsService {


    @Override
    public BaseResult deleteComment(String commentId) {
        TbComments comments = new TbComments();
        comments.setId(commentId);
        comments.setIsDelete("1");
        this.updateById(comments);
        return BaseResult.success();
    }
}
