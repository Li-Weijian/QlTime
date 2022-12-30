package com.qltime.service;

import com.qltime.model.dto.CharNode;
import com.qltime.model.entity.TbWeight;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2020-05-22
 */
public interface TbWeightService extends IService<TbWeight>{


    /**
     * 获取图表数据
     * @param day
     * @return
     */
    List<CharNode> getChart(String day);

    /**
     * 添加体重
     * @param weight
     * @param loginUserId
     */
    void addWeight(Double weight, Integer loginUserId);

    List<TbWeight> getWeightList(String day);

    List<CharNode> tranCharNode(List<TbWeight> weightList);
}
