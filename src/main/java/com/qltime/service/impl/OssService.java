package com.qltime.service.impl;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author liweijian
 * @date 2023/1/31 12:41
 */
public interface OssService {


    /**
     * 批量上传文件
     * @param files
     * @return
     */
    List<String> upload(MultipartFile[] files);

    /**
     * 上传文件
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
