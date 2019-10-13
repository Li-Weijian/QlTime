package com.lovezz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: liweijian
 * @Date: 2019/10/12 20:56
 * @Description:
 */
@Controller
@RequestMapping("/ossController")
public class OssController {

    /**
     * @Function: 图片上传
     */
    @PostMapping("/postImages")
    public Object fileUpload(@RequestParam(value = "fileupload", required = false) MultipartFile file) {

        return null;
    }
}
