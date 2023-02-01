package com.qltime.controller;

import com.qltime.constant.GalleryFlagEnum;
import com.qltime.constant.MsgCommon;
import com.qltime.model.dto.BaseResult;
import com.qltime.service.impl.OssService;
import com.qltime.service.TbGalleryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

/**
 * @Auther: liweijian
 * @Date: 2019/10/12 20:56
 * @Description:
 */
@RestController
@RequestMapping("/ossController")
public class OssController {


    private final OssService ossService;

    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    /**
     * @Function: 图片上传
     */
    @PostMapping("/postImages")
    public BaseResult uploadImages(@RequestParam(value = "file", required = false) MultipartFile[] file) {
        return BaseResult.success(MsgCommon.SUCCESS.getMessage(), ossService.upload(file));
    }

}
