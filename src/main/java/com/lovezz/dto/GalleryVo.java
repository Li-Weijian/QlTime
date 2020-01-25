package com.lovezz.dto;

import java.util.List;

/**
 * @auther: liweijian
 * @Date: 2020/1/23 20:47
 * @Description:
 */
public class GalleryVo  {

    List<String> imageUrl;

    Integer count;

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
