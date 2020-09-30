package com.lovezz.dto;

import com.lovezz.entity.TbComments;
import com.lovezz.entity.TbGallery;
import com.lovezz.entity.TbTops;
import lombok.Data;

import java.util.List;

/**
 * @Auther: liweijian
 * @Date: 2019/11/5 17:39
 * @Description: 留言板数据传输对象
 */
@Data
public class TopsDTO {
    
    //留言
    private TbTops tops;

    //图片列表
    private List<TbGallery> galleryList;

    //评论列表
    private List<List<TbComments>> commentsList;
}
