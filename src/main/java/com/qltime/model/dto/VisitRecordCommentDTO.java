package com.qltime.model.dto;

import com.qltime.constant.CommentType;
import com.qltime.model.entity.VisitRecord;
import com.qltime.model.entity.VisitRecordComment;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author liweijian
 * @date 2023/2/2 19:50
 */
@Data
public class VisitRecordCommentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 内容
     */
    private String content;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 记录ID
     */
    private String recordId;

    /**
     * 是否删除
     */
    private String isDelete;
    /**
     * 被回复用户ID
     */
    private Integer replayUserId;

    /**
     * 上层ID
     */
    private String lastId;
    /**
     * 类型 0：评论 1：回复
     */
    private CommentType type;

    /**
     * 评论时间
     */
    private Date created;

    /**
     * 评论的用户信息
     */
    private UserDTO user;

    /**
     * 被回复的用户信息
     */
    private UserDTO replayUser;

    /**
     * 回复列表
     */
    private List<VisitRecordCommentDTO> replayCommentList;


    public static VisitRecordCommentDTO of (VisitRecordComment comment, BiConsumer<VisitRecordComment, VisitRecordCommentDTO> consumer){
        VisitRecordCommentDTO dto = new VisitRecordCommentDTO();
        BeanUtils.copyProperties(comment, dto);
        // 额外自定义处理
        if (Objects.nonNull(consumer)){
            consumer.accept(comment, dto);
        }
        return dto;
    }

}
