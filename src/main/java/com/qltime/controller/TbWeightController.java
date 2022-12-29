package com.qltime.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qltime.dto.BaseResult;
import com.qltime.dto.HealthDTO;
import com.qltime.entity.TbWeight;
import com.qltime.service.TbWeightService;
import com.qltime.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liweijian123
 * @since 2020-05-22
 */
@Controller
@RequestMapping("/weight")
@CrossOrigin("*")
public class TbWeightController {

    @Autowired
    private TbWeightService weightService;


    @GetMapping("/getMenuList")
    @ResponseBody
    public BaseResult getMenuList() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        TbWeight weight = weightService.selectOne(new EntityWrapper<TbWeight>().eq("isDelete", "0").eq("userId", new RequestUtils().getLoginUserId())
                .orderBy("created", false));
        HealthDTO healthDTO = new HealthDTO();
        healthDTO.setData(String.valueOf(weight.getWeight()));
        healthDTO.setDate(simpleDateFormat.format(weight.getCreated()));
        healthDTO.setUnit("千克");

        return BaseResult.success("操作成功", healthDTO);
    }

    @GetMapping("/getChart")
    @ResponseBody
    public BaseResult getChart(String day){
        if (StringUtils.isBlank(day)){
            day = "10";
        }

        return BaseResult.success("操作成功", weightService.getChart(day));
    }

    @GetMapping("/addWeight")
    @ResponseBody
    public BaseResult addWeight(@RequestParam(value = "weight") Double weight){
        weightService.addWeight(weight,new RequestUtils().getLoginUserId());

        return BaseResult.success("操作成功");
    }

    @GetMapping("/isExist")
    @ResponseBody
    public BaseResult isExist(){
        List<TbWeight> existList = weightService.selectList(new EntityWrapper<TbWeight>().eq("created", DateUtil.today())
                .eq("userId",new RequestUtils().getLoginUserId()).eq("isDelete","0"));
        if (existList.size()>0){
            //已存在
            return BaseResult.success("操作成功",1);
        }else {
            return BaseResult.success("操作成功",0);
        }
    }

    @GetMapping("/getWeightList")
    @ResponseBody
    public BaseResult getWeightList(String day){
        if (StringUtils.isBlank(day)){
            day = "10";
        }
        return BaseResult.success("操作成功",weightService.tranCharNode(weightService.getWeightList(day)));
    }
}

