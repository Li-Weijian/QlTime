package com.lovezz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.dto.ImageInfoDTO;
import com.lovezz.entity.TbGallery;
import com.lovezz.mapper.TbGalleryMapper;
import com.lovezz.service.TbGalleryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lovezz.utils.OssUtil;
import com.lovezz.utils.URLUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-12
 */
@Service
public class TbGalleryServiceImpl extends ServiceImpl<TbGalleryMapper, TbGallery> implements TbGalleryService {

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private TbGalleryMapper galleryMapper;


    @Override
    public String fileUpload(MultipartFile file) throws MalformedURLException {
        //上传图片
        String url = ossUtil.checkImage(file);
        //无参数的连接 http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg
        String host = URLUtils.getPath(url);

        //保存url
        TbGallery gallery = new TbGallery();
        gallery.setId(UUID.randomUUID().toString());
        gallery.setUrl(host);
        gallery.setUploadDate(new Date());

        ImageInfoDTO imageInfo = ossUtil.getImageInfo(host);
        gallery.setFormat(imageInfo.getFormat().getValue());
        gallery.setImageHeight(imageInfo.getImageHeight().getValue());
        gallery.setImageWidth(imageInfo.getImageWidth().getValue());
        gallery.setFilesize(imageInfo.getFileSize().getValue());
        gallery.setFileName(file.getOriginalFilename());

        galleryMapper.insert(gallery);
        return url;
    }

    @Override
    public List<TbGallery> selectGalleryList(Integer action, Integer page) {
        Integer limit = 20;
        if (page < 1){
            page = 1;
        }
        return galleryMapper.selectPage(new RowBounds((page-1)*20,limit),new EntityWrapper<TbGallery>().orderBy("uploadDate",false));
    }
}
