package com.qltime.service;

import com.qltime.dto.BaseResult;
import com.qltime.entity.TbComments;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2019-11-03
 */
public interface TbCommentsService extends IService<TbComments> {

    BaseResult deleteComment(String commentId);

}
