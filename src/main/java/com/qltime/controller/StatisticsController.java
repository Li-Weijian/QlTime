package com.qltime.controller;

import com.qltime.model.dto.BaseResult;
import com.qltime.exception.CommonException;
import com.qltime.service.StatisticsService;
import com.qltime.service.TbUserService;
import com.qltime.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liweijian
 * @Date: 2020/10/12 21:48
 * @Description:
 */
@RestController
@RequestMapping("/statistics")
@CrossOrigin("*")
@Slf4j
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private TbUserService userService;

    /**
     * 获取相关模块数据统计
     * @return
     */
    @GetMapping("/")
    public BaseResult selectAllCount(){
        Integer userId = new RequestUtils().getLoginUserId();
        try {
            return statisticsService.selectAllCount(userService.selectAllIds(userId));
        } catch (CommonException e) {
            log.error("【获取相关模块数据统计】:{}", e);
            return BaseResult.fail(e.getStatus(), e.getMessage());
        }
    }


}
