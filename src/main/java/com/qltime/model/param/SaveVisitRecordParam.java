package com.qltime.model.param;

import lombok.Data;

import java.util.List;

/**
 * @author liweijian
 * @date 2023/1/31 14:52
 */
@Data
public class SaveVisitRecordParam {

    /**
     *  标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片列表
     */
    private List<String> imageList;

    /**
     * 标签列表 tagName
     */
    private List<String> tagList;

}
