package com.lovezz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.dto.BaseResult;
import com.lovezz.dto.HealthDTO;
import com.lovezz.entity.TbWeight;
import com.lovezz.service.TbWeightService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

        TbWeight weight = weightService.selectOne(new EntityWrapper<TbWeight>().orderBy("created", false));
        HealthDTO healthDTO = new HealthDTO();
        healthDTO.setData(String.valueOf(weight.getWeight()));
        healthDTO.setDate(simpleDateFormat.format(weight.getCreated()));
        healthDTO.setUnit("千克");

        return BaseResult.success("操作成功", healthDTO);
    }




}

