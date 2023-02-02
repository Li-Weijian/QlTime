package com.qltime.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qltime.model.dto.BaseResult;
import com.qltime.model.dto.VisitRecordDTO;
import com.qltime.model.entity.VisitRecord;
import com.qltime.model.param.SaveVisitRecordParam;
import com.qltime.service.VisitRecordService;
import com.qltime.utils.DataScopeHelper;
import com.qltime.utils.RequestUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 探店记录 前端控制器
 * </p>
 *
 * @author liweijian123
 * @since 2023-01-31
 */
@RestController
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

    @GetMapping("/delete")
    public BaseResult delete(@RequestParam String recordId){
        visitRecordService.delete(recordId);
        return BaseResult.success();
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    public BaseResult<IPage<VisitRecordDTO>> list(@RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                                  @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo){
        return BaseResult.success(visitRecordService.page(pageNo, pageSize));
    }

    /**
     * 获取详情
     * @return
     */
    @GetMapping("/detail")
    public BaseResult<VisitRecordDTO> detail(@RequestParam("recordId") String recordId){
        return BaseResult.success(visitRecordService.detail(recordId));
    }
}

