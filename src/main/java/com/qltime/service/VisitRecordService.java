package com.qltime.service;

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
}
