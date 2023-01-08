package com.qltime.controller;

import com.qltime.model.dto.BaseResult;
import com.qltime.service.PushDailyWechat;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liweijian
 * @date 2022/12/29 19:37
 */
@RestController
@RequestMapping("/pushDaily")
public class PushDailyController {

    @Autowired
    private PushDailyWechat pushDailyWechat;


    @GetMapping("/pushWechat")
    public BaseResult pushWechat() {
        pushDailyWechat.pushWechat();
        return BaseResult.success();
    }


}
