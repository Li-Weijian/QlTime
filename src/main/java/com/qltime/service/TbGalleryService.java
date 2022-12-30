package com.qltime.service;

import com.qltime.model.dto.BaseResult;
import com.qltime.model.dto.GalleryVo;
import com.qltime.model.entity.TbGallery;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-12
 */
public interface TbGalleryService extends IService<TbGallery> {

    String fileUpload(MultipartFile file) throws MalformedURLException;
    String fileUpload(MultipartFile file, String flag) throws MalformedURLException;
    List<String> fileUpload(MultipartFile[] file, String flag) throws MalformedURLException;

    List<TbGallery> selectGalleryList(Integer action, Integer page);

    GalleryVo selectGalleryWrapper(List<Integer> ids);

    /**
     * 构建图库对象
     * @return
     */
    TbGallery makeGallery(String url, String topsId, String flag, String fileName);

    /**
     * 保存回忆图片
     * @param imageUrl
     * @return
     */
    BaseResult saveMemory(List<String> imageUrl);
}
