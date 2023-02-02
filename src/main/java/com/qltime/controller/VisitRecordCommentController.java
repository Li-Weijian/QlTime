package com.qltime.controller;


import com.qltime.constant.CommentType;
import com.qltime.model.dto.BaseResult;
import com.qltime.model.entity.VisitRecordComment;
import com.qltime.service.VisitRecordCommentService;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/visitRecordComment")
public class VisitRecordCommentController {

    private final VisitRecordCommentService visitRecordCommentService;

    public VisitRecordCommentController(VisitRecordCommentService visitRecordCommentService) {
        this.visitRecordCommentService = visitRecordCommentService;
    }

    /**
     * 评论
     * @param comment
     * @return
     */
    @PostMapping("/doComment")
    public BaseResult<Void> doComment(@RequestBody VisitRecordComment comment){
        visitRecordCommentService.doComment(comment);
        return BaseResult.success();
    }


    /**
     * 获取评论详情
     * @param recordId
     * @return
     */
    @GetMapping("/commentDetail")
    public BaseResult commentDetail(@RequestParam String recordId){
        return BaseResult.success(visitRecordCommentService.commentDetail(recordId, CommentType.COMMENT));


    }


}

