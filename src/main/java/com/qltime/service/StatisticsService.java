package com.qltime.service;

import com.qltime.model.dto.BaseResult;

import java.util.List;

/**
 * @auther: liweijian
 * @Date: 2020/10/12 21:54
 * @Description:
 */
public interface StatisticsService {

    /**
     * 获取相关模块数据统计
     * @param selectAllIds
     * @return
     */
    BaseResult selectAllCount(List<Integer> selectAllIds);

}
