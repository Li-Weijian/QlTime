package com.lovezz.service;

import com.lovezz.dto.BaseResult;
import com.lovezz.dto.TopsDTO;
import com.lovezz.entity.TbTops;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2019-11-03
 */
public interface TbTopsService extends IService<TbTops> {

    /**
     * 发表日志
     * @auther: liweijian
     * @date: 2019/11/4 22:39
     */
    boolean publishTops(MultipartFile[] file, String topText) throws MalformedURLException;


    /**
     * 获取留言列表
     * @auther: liweijian
     * @date: 2019/11/5 17:41
     */
    List<TopsDTO> getTopsList();

    /**
     * 删除留言
     * @auther: liweijian
     * @date: 2019/11/6 20:34
     */
    BaseResult deleteTops(String topsId);

}
