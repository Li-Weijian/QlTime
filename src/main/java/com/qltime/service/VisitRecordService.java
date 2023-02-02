package com.qltime.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qltime.model.dto.VisitRecordDTO;
import com.qltime.model.entity.VisitRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qltime.model.param.SaveVisitRecordParam;

/**
 * <p>
 * 探店记录 服务类
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
public interface VisitRecordService extends IService<VisitRecord> {

    /**
     * 保存
     * @param saveVisitRecordParam
     */
    void save(SaveVisitRecordParam saveVisitRecordParam);

    /**
     * 获取分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<VisitRecordDTO> page(Integer pageNo, Integer pageSize);

    /**
     * 获取探店详情
     * @param recordId
     * @return
     */
    VisitRecordDTO detail(String recordId);

    /**
     * 删除探店记录
     * @param recordId
     */
    void delete(String recordId);

    /**
     * 增加评论数
     * @param recordId
     */
    void incrCommentNumber(String recordId);
}
