package com.lovezz.controller;

import com.lovezz.annotation.OperationEmailDetail;
import com.lovezz.constant.MsgCommon;
import com.lovezz.constant.OperationModule;
import com.lovezz.dto.BaseResult;
import com.lovezz.dto.GalleryVo;
import com.lovezz.dto.TopsDTO;
import com.lovezz.entity.TbTops;
import com.lovezz.exception.CommonException;
import com.lovezz.service.TbCommentsService;
import com.lovezz.service.TbTopsService;
import com.lovezz.service.TbUserService;
import com.lovezz.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liweijian
 * @Date: 2019/11/3 17:22
 * @Description: 留言板控制器
 */
@Controller
@RequestMapping("/tops")
@CrossOrigin("*")
@Slf4j
public class TopsController {


    @Autowired
    private TbTopsService topsService;

    @Autowired
    private TbCommentsService commentsService;

    @Autowired
    private TbUserService userService;

    @RequestMapping("/toTops")
    public ModelAndView toTops(ModelAndView modelAndView){
        List<TopsDTO> topsList = null;
        try {
            topsList = topsService.getTopsList(new RequestUtils().getLoginUser().getId());
            modelAndView.addObject("topsList", topsList);
            modelAndView.setViewName("tops/index2");
        } catch (CommonException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @RequestMapping("/toPublish")
    public String toPublish(){

        return "tops/publish2";
    }

    @RequestMapping("/publishTops")
    @ResponseBody
    @OperationEmailDetail(content = "新添加了一条【小日常】啦，快打开App查看吧", operationClass = OperationModule.TOPS)
    public Map<String,Object> publishTops(@RequestParam("file") MultipartFile[] file, @RequestParam("topText") String topText) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        boolean isSucc = topsService.publishTops(file,topText);
        if (isSucc){
            resultMap.put("code",200);
            resultMap.put("message","发表成功");
        }else {
            resultMap.put("code",500);
            resultMap.put("message","发表失败");
        }

        return resultMap;
    }

    @RequestMapping("/deleteTops")
    @ResponseBody
    public BaseResult deleteTops(String topsId, String flag){
        if (StringUtils.isNotBlank(flag) && "0".equals(flag)){
            //删除说说
            return topsService.deleteTops(topsId);
        }else if (StringUtils.isNotBlank(flag) && "1".equals(flag)){
            //删除评论
            return commentsService.deleteComment(topsId);
        }
        return BaseResult.fail("删除失败");
    }

    /**
     * 发表评论
     * @auther: liweijian
     * @date: 2020/1/1 17:53
     */
    @RequestMapping("/doCommont")
    @ResponseBody
    @OperationEmailDetail(content = "【小日常】收到一条新评论啦，快打开App查看吧",operationClass = OperationModule.TOPS)
    public BaseResult doCommont(String topId, String content, String flag){

        return topsService.doCommont(topId,content,flag);
    }

    /**
     *
     * 获取分页数据
     * @auther: liweijian
     * @date: 2020/2/24 21:19
     */
    @RequestMapping("/getTopsList")
    @ResponseBody
    public BaseResult getTopsList(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit){

        try {

            Integer userId = new RequestUtils().getLoginUserId();
            List<TopsDTO> topsList = null;
            topsList = topsService.getTopsList(offset, limit, userService.selectAllIds(userId));
            log.info("进入 TopsList :{}", userId);
            return BaseResult.success("操作成功", topsList);
        } catch (CommonException e) {
            log.error("【获取小日常列表】: {}" ,e.getMessage());
            return BaseResult.fail(e.getStatus(), e.getMessage());
        }

    }

    /***
     * 适配微信小程序的接口，因为微信小程序不能多文件上传，所以只能将上传文件和发表接口解耦。
     * 上传 小日常 图片
     * @param file
     * @return
     */
    @PostMapping("/uploadTopsImages")
    @ResponseBody
    public BaseResult uploadTopsImages(@RequestParam("file") MultipartFile[] file){
        List<String> result = null;
        try {
            result = topsService.uploadTopsImages(file);
        } catch (MalformedURLException e) {
            return BaseResult.fail(MsgCommon.URL_ERROR.getStatus(), e.getMessage());
        }
        return BaseResult.success(MsgCommon.SUCCESS.getMessage(), result);
    }

    /**
     * 适配微信小程序的接口，因为微信小程序不能多文件上传，所以只能将上传文件和发表接口解耦。
     * @return
     */
    @PostMapping("/publishTopsByWx")
    @ResponseBody
    public BaseResult publishTopsByWx(@RequestBody GalleryVo galleryVo){
        try {
            boolean isSucc = topsService.publishTops(galleryVo.getImageUrl() ,galleryVo.getTopText());
            return MsgCommon.SUCCESS;
        } catch (MalformedURLException e) {
            log.error("【发布小日常】: {}", e.getMessage());
            return MsgCommon.ERROR;
        }

    }


}
