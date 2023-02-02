package com.qltime.model.dto;

import com.qltime.model.entity.Tag;
import com.qltime.model.entity.TbUser;
import com.qltime.model.entity.VisitRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author liweijian
 * @date 2023/2/2 12:11
 */
@Data
public class VisitRecordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表时间
     */
    private Date created;
    /**
     * 发表用户
     */
    private Integer userId;
    /**
     * 是否删除
     */
    private Integer isDelete;
    /**
     * 赞
     */
    private Integer likeNum;
    /**
     * 评论数
     */
    private Integer comment;

    /**
     * 图片列表
     */
    private List<String> imageList;

    /**
     * 用户信息
     */
    private UserDTO user;

    /**
     * 标签列表
     */
    private List<Tag> tagList;


    public static VisitRecordDTO of (VisitRecord visitRecord,  BiConsumer<VisitRecord, VisitRecordDTO> consumer){
        VisitRecordDTO dto = new VisitRecordDTO();
        dto.setId(visitRecord.getId());
        dto.setTitle(visitRecord.getTitle());
        dto.setContent(visitRecord.getContent());
        dto.setCreated(visitRecord.getCreated());
        dto.setUserId(visitRecord.getUserId());
        dto.setIsDelete(visitRecord.getIsDelete());
        dto.setLikeNum(visitRecord.getLikeNum());
        dto.setComment(visitRecord.getComment());

        // 额外自定义处理
        if (Objects.nonNull(consumer)){
            consumer.accept(visitRecord, dto);
        }
        return dto;
    }

}
