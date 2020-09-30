package com.lovezz.dto;

import lombok.Data;

import java.util.List;

/**
 * @auther: liweijian
 * @Date: 2020/1/23 20:47
 * @Description:
 */
@Data
public class GalleryVo  {

    List<String> imageUrl;

    Integer count;

    String topText;
}
