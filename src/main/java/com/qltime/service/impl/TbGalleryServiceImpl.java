package com.qltime.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.qltime.constant.GalleryFlagEnum;
import com.qltime.model.dto.BaseResult;
import com.qltime.model.dto.GalleryVo;
import com.qltime.model.dto.ImageInfoDTO;
import com.qltime.model.entity.TbGallery;
import com.qltime.mapper.TbGalleryMapper;
import com.qltime.service.TbGalleryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qltime.utils.OssUtil;
import com.qltime.utils.RequestUtils;
import com.qltime.utils.URLUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private TbGalleryService galleryService;


    @Override
    public String fileUpload(MultipartFile file, String flag) throws MalformedURLException {
        //上传图片
        String url = ossUtil.checkImage(file);
        //无参数的连接 http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg
        String host = URLUtils.getPath(url);

        //保存url
        TbGallery gallery = new TbGallery();
        gallery.setId(UUID.randomUUID().toString());
        gallery.setUrl(host);
        gallery.setUploadDate(new Date());
        gallery.setFlag(flag);

        ImageInfoDTO imageInfo = ossUtil.getImageInfo(host);
        gallery.setFormat(imageInfo.getFormat().getValue());
        gallery.setImageHeight(imageInfo.getImageHeight().getValue());
        gallery.setImageWidth(imageInfo.getImageWidth().getValue());
        gallery.setFilesize(imageInfo.getFileSize().getValue());
        gallery.setFileName(file.getOriginalFilename());
        gallery.setUserId(new RequestUtils().getLoginUserId());

        galleryMapper.insert(gallery);
        return host;
    }

    @Override
    public List<String> fileUpload(MultipartFile[] fileList, String flag) throws MalformedURLException {
        List<String> urlList = new ArrayList<>();
        for (MultipartFile file : fileList) {
            urlList.add(galleryService.fileUpload(file, flag));
        }

        return urlList;
    }


    @Override
    public String fileUpload(MultipartFile file) throws MalformedURLException {
        return galleryService.fileUpload(file, String.valueOf(GalleryFlagEnum.GALLERY.getType()));
    }

    @Override
    public List<TbGallery> selectGalleryList(Integer action, Integer page) {
        Integer limit = 20;
        if (page < 1){
            page = 1;
        }
        return galleryMapper.selectPage(new RowBounds((page-1)*20,limit),new EntityWrapper<TbGallery>().eq("flag","0").orderBy("uploadDate",false));
    }

    @Override
    public GalleryVo selectGalleryWrapper(List<Integer> ids) {
        GalleryVo galleryVo = null;
        Wrapper<TbGallery> wrapper = new EntityWrapper<TbGallery>().eq("flag", 0);
        wrapper.in("userId", ids);
        wrapper.orderBy("uploadDate", false);

        List<TbGallery> galleryList = galleryMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(galleryList)){
            galleryVo = new GalleryVo();
            galleryVo.setImageUrl(galleryList.stream().map(item -> {return item.getUrl();}).collect(Collectors.toList()));
            galleryVo.setCount(galleryList.size());
            galleryVo.setGalleryList(galleryList);
        }
        return galleryVo;
    }

    @Override
    public TbGallery makeGallery(String url, String topsId, String flag, String fileName) {
        TbGallery gallery = new TbGallery();
        gallery.setId(UUID.randomUUID().toString());
        gallery.setUrl(url);
        gallery.setUploadDate(new Date());

        ImageInfoDTO imageInfo = ossUtil.getImageInfo(url);
        gallery.setFormat(imageInfo.getFormat().getValue());
        gallery.setImageHeight(imageInfo.getImageHeight().getValue());
        gallery.setImageWidth(imageInfo.getImageWidth().getValue());
        gallery.setFilesize(imageInfo.getFileSize().getValue());
        gallery.setFileName(fileName);
        gallery.setUserId(new RequestUtils().getLoginUserId());
        gallery.setTopId(topsId);
        gallery.setFlag(flag);

        galleryMapper.insert(gallery);

        return gallery;
    }

    @Override
    public BaseResult saveMemory(List<String> imageUrl) {
        for (String url : imageUrl) {
            this.makeGallery(url, null, String.valueOf(GalleryFlagEnum.GALLERY.getType()), "memoryImage");
        }
        return BaseResult.success();
    }

}
