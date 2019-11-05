package com.lovezz.dto;

import com.lovezz.entity.TbComments;
import com.lovezz.entity.TbGallery;
import com.lovezz.entity.TbTops;

import java.util.List;

/**
 * @Auther: liweijian
 * @Date: 2019/11/5 17:39
 * @Description: 留言板数据传输对象
 */

public class TopsDTO {


    //留言
    private TbTops tops;

    //图片列表
    private List<TbGallery> galleryList;

    //评论列表
    private List<TbComments> commentsList;


    public TbTops getTops() {
        return tops;
    }

    public void setTops(TbTops tops) {
        this.tops = tops;
    }

    public List<TbGallery> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(List<TbGallery> galleryList) {
        this.galleryList = galleryList;
    }

    public List<TbComments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<TbComments> commentsList) {
        this.commentsList = commentsList;
    }
}
