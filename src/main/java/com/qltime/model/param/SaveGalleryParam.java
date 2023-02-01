package com.qltime.model.param;

import com.qltime.constant.GalleryFlagEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author liweijian
 * @date 2023/1/31 12:59
 */
@Data
@Builder
public class SaveGalleryParam {

    /**
     * 图片URL
     */
    private List<String> urls;

    /**
     * 图片类型
     */
    private GalleryFlagEnum flag;

    /**
     * 业务id
     */
    private String topsId;
}
