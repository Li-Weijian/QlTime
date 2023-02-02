package com.qltime.service;

import com.qltime.constant.CommentType;
import com.qltime.model.dto.VisitRecordCommentDTO;
import com.qltime.model.entity.VisitRecordComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
public interface VisitRecordCommentService extends IService<VisitRecordComment> {

    /**
     * 评论
     * @param comment
     */
    void doComment(VisitRecordComment comment);

    /**
     * 获取评论详情
     * @param recordId
     * @return
     */
    List<VisitRecordCommentDTO> commentDetail(String recordId, CommentType commentType);
}
