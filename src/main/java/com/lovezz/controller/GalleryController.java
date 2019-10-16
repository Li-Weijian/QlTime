package com.lovezz.controller;

import com.lovezz.entity.TbGallery;
import com.lovezz.service.TbGalleryService;
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
     */
    @RequestMapping("/postImages")
    public String fileUpload(@RequestParam(value = "fileupload", required = false) MultipartFile file) throws MalformedURLException {
        galleryService.fileUpload(file);
        return "redirect:/galleryController/toGallery";
    }








}