package com.qltime.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qltime.constant.CommentType;
import com.qltime.model.dto.VisitRecordCommentDTO;
import com.qltime.model.entity.VisitRecordComment;
import com.qltime.mapper.VisitRecordCommentMapper;
import com.qltime.service.TbUserService;
import com.qltime.service.VisitRecordCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qltime.service.VisitRecordService;
import com.qltime.utils.RequestUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
@Service
public class VisitRecordCommentServiceImpl extends ServiceImpl<VisitRecordCommentMapper, VisitRecordComment> implements VisitRecordCommentService {

    private final VisitRecordService visitRecordService;
    private final TbUserService userService;

    public VisitRecordCommentServiceImpl(VisitRecordService visitRecordService, TbUserService userService) {
        this.visitRecordService = visitRecordService;
        this.userService = userService;
    }

    @Override
    public void doComment(VisitRecordComment comment) {
        if (null == comment.getUserId()) {
            comment.setUserId(RequestUtils.getLoginUserId());
        }
        if (CommentType.COMMENT.equals(comment.getType())){
            // 第一层评论的lastId是探店记录
            comment.setLastId(comment.getRecordId());
        }
        save(comment);

        // 探店记录评论数自增
        visitRecordService.incrCommentNumber(comment.getRecordId());

    }

    @Override
    public List<VisitRecordCommentDTO> commentDetail(String recordId, CommentType commentType) {
        return list(new LambdaQueryWrapper<VisitRecordComment>()
            .eq(VisitRecordComment::getLastId, recordId)
            .eq(VisitRecordComment::getType, commentType)
            .orderBy(false, false, VisitRecordComment::getCreated)
        ).parallelStream()
            .map(comment ->
                VisitRecordCommentDTO.of(comment, (e, v) -> {
                    v.setUser(userService.getUserById(e.getUserId()));
                    if (e.getReplayUserId() != null) {
                        v.setReplayUser(userService.getUserById(e.getReplayUserId()));
                    }
                    // 递归找出回复列表
                    v.setReplayCommentList(commentDetail(e.getId(), CommentType.REPLAY));
                })
            ).collect(Collectors.toList());
    }


}
