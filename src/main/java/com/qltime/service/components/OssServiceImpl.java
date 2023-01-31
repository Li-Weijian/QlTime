package com.qltime.service.components;

import com.qltime.service.impl.OssService;
import com.qltime.utils.OssUtil;
import com.qltime.utils.URLUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liweijian
 * @date 2023/1/31 12:41
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    private final OssUtil ossUtil;

    public OssServiceImpl(OssUtil ossUtil) {
        this.ossUtil = ossUtil;
    }


    /**
     * 批量上传文件
     *
     * @param files
     * @return
     */
    @Override
    public List<String> upload(MultipartFile[] files) {

        List<String> urls= new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(upload(file));
        }
        return urls;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        //上传图片
        String url = ossUtil.checkImage(file);
        //无参数的连接 http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg
        try {
            return URLUtils.getPath(url);
        } catch (MalformedURLException e) {
            log.error("[上传文件] 获取链接失败. {}", url);
        }
        return null;
    }
}
