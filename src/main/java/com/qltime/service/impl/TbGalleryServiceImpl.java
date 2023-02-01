package com.qltime.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qltime.constant.GalleryFlagEnum;
import com.qltime.mapper.TbGalleryMapper;
import com.qltime.model.dto.BaseResult;
import com.qltime.model.dto.GalleryVo;
import com.qltime.model.dto.ImageInfoDTO;
import com.qltime.model.entity.TbGallery;
import com.qltime.model.param.SaveGalleryParam;
import com.qltime.service.TbGalleryService;
import com.qltime.utils.OssUtil;
import com.qltime.utils.RequestUtils;
import com.qltime.utils.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

        save(gallery);
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
        int limit = 20;
        if (page < 1){
            page = 1;
        }
        return page(new Page<TbGallery>((page-1)* 20L, limit),
            new QueryWrapper<TbGallery>().eq("flag", GalleryFlagEnum.GALLERY.getType().toString())
                .orderBy(false, false, "uploadDate")
        ).getRecords();
    }

    @Override
    public GalleryVo selectGalleryWrapper(List<Integer> ids) {
        GalleryVo galleryVo = null;
        QueryWrapper<TbGallery> wrapper = new QueryWrapper<TbGallery>().eq("flag", 0);
        wrapper.in("userId", ids);
        wrapper.orderBy(false, false, "uploadDate");

        List<TbGallery> galleryList = list(wrapper);
        if (CollectionUtil.isNotEmpty(galleryList)){
            galleryVo = new GalleryVo();
            galleryVo.setImageUrl(galleryList.stream().map(TbGallery::getUrl).collect(Collectors.toList()));
            galleryVo.setCount(galleryList.size());
            galleryVo.setGalleryList(galleryList);
        }
        return galleryVo;
    }

    @Override
    public TbGallery makeGallery(String url, String topsId, GalleryFlagEnum flag, String fileName) {
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
        gallery.setUserId(RequestUtils.getLoginUserId());
        gallery.setTopId(topsId);
        gallery.setFlag(flag.getType().toString());

        save(gallery);

        return gallery;
    }

    @Override
    public BaseResult saveMemory(List<String> imageUrl) {
        for (String url : imageUrl) {
            this.makeGallery(url, null, GalleryFlagEnum.GALLERY, FileUtil.getName(url));
        }
        return BaseResult.success();
    }

    /**
     * 保存图库
     *
     * @param param
     */
    @Override
    public void saveGallery(SaveGalleryParam param) {
        param.getUrls().forEach(url -> {
            this.makeGallery(url, param.getTopsId(), param.getFlag(), FileUtil.getName(url));
        });
    }

}
