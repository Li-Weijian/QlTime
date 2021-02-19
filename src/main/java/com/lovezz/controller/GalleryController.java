package com.lovezz.controller;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lovezz.annotation.OperationEmailDetail;
import com.lovezz.constant.GalleryFlagEnum;
import com.lovezz.constant.MsgCommon;
import com.lovezz.constant.OperationModule;
import com.lovezz.dto.BaseResult;
import com.lovezz.entity.TbGallery;
import com.lovezz.exception.CommonException;
import com.lovezz.service.TbGalleryService;
import com.lovezz.service.TbUserService;
import com.lovezz.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.util.List;

/**
 * @Auther: liweijian
 * @Date: 2019/10/12 22:09
 * @Description:
 */

@Controller
@RequestMapping("/galleryController")
@CrossOrigin("*")
@Slf4j
public class GalleryController {

    @Autowired
    private TbGalleryService galleryService;

    @Autowired
    private TbUserService userService;

    @RequestMapping("/toGallery")
    public ModelAndView toGallery(ModelAndView modelAndView,Integer action,@RequestParam(defaultValue = "1") Integer page){
        if (page < 1){
            page = 1;
        }
        List<TbGallery> galleryList = galleryService.selectGalleryList(action,page);
        int count = galleryService.selectCount(null);
        modelAndView.addObject("galleryList",galleryList);
        modelAndView.addObject("action",action);
        modelAndView.addObject("page",page);
        modelAndView.addObject("count",count);
        modelAndView.setViewName("gallery/index");
        return modelAndView;
    }

    /**
     * @Function: 图片上传
     * 适用本项目前端上传
     */
    @RequestMapping("/postImages")
    @OperationEmailDetail(content = "新添加了一条【共同记忆】啦，快打开App查看吧", operationClass = OperationModule.GALLERY)
    public String fileUpload(@RequestParam(value = "fileupload", required = false) MultipartFile file) throws MalformedURLException {
        galleryService.fileUpload(file);
        return "redirect:/galleryController/toGallery";
    }

    /**
     *
     * 获取图片列表，获取全部，前端使用懒加载动态加载
     * @auther: liweijian
     * @date: 2020/1/23 21:34
     */
    @RequestMapping("/getGallery")
    @ResponseBody
    public BaseResult getGallery(){
        Integer userId = new RequestUtils().getLoginUserId();
        try {
            log.info("进入 图片列表:{}", userId);
            return BaseResult.success(MsgCommon.SUCCESS.getMessage(),
                    galleryService.selectGalleryWrapper(userService.selectAllIds(userId)));
        } catch (CommonException e) {
            log.error("【获取图片列表】: {}", e);
            return BaseResult.fail(e.getStatus(), e.getMessage());
        }
    }

    /**
     * 图片上传 适用前后端分离
     */
    @RequestMapping("/uploadImages")
    @ResponseBody
    @OperationEmailDetail(content = "新添加了一条【共同记忆】啦，快打开App查看吧", operationClass = OperationModule.GALLERY)
    public BaseResult uploadImages(@RequestParam(value = "fileupload", required = false) MultipartFile[] file) throws MalformedURLException {
        List<String> urlList = galleryService.fileUpload(file, String.valueOf(GalleryFlagEnum.GALLERY.getType()));
        return BaseResult.success(MsgCommon.SUCCESS.getMessage(), urlList);
    }

    @PostMapping("/saveMemory")
    @ResponseBody
    public BaseResult saveMemory(@RequestBody String imageListStr) {
        List<String> imageUrl = JSONObject.parseArray(imageListStr, String.class);
        return galleryService.saveMemory(imageUrl);
    }




}
