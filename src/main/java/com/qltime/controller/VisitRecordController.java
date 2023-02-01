package com.qltime.controller;


import com.qltime.model.dto.BaseResult;
import com.qltime.model.param.SaveVisitRecordParam;
import com.qltime.service.VisitRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 探店记录 前端控制器
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
@Controller
@RequestMapping("/visitRecord")
public class VisitRecordController {

    private final VisitRecordService visitRecordService;

    public VisitRecordController(VisitRecordService visitRecordService) {
        this.visitRecordService = visitRecordService;
    }

    /**
     * 保存
     * @param saveVisitRecordParam
     * @return
     */
    @PostMapping("/save")
    public BaseResult save(@RequestBody SaveVisitRecordParam saveVisitRecordParam){
        visitRecordService.save(saveVisitRecordParam);
        return BaseResult.success();
    }



}

