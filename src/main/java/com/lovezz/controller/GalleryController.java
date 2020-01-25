package com.lovezz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lovezz.annotation.OperationEmailDetail;
import com.lovezz.constant.OperationModule;
import com.lovezz.dto.BaseResult;
import com.lovezz.entity.TbGallery;
import com.lovezz.service.TbGalleryService;
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
public class GalleryController {

    @Autowired
    private TbGalleryService galleryService;

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
        return BaseResult.success("成功",galleryService.selectGalleryWrapper());
    }

    /**
     * 图片上传 适用前后端分离
     */
    @RequestMapping("/uploadImages")
    @ResponseBody
    @OperationEmailDetail(content = "新添加了一条【共同记忆】啦，快打开App查看吧", operationClass = OperationModule.GALLERY)
    public BaseResult uploadImages(@RequestParam(value = "fileupload", required = false) MultipartFile[] file) throws MalformedURLException {
        List<String> urlList = galleryService.fileUpload(file,"2");
        return BaseResult.success("成功", urlList);
    }











}
