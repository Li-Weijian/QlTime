package com.lovezz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lovezz.dto.BaseResult;
import com.lovezz.dto.ImageInfoDTO;
import com.lovezz.dto.TopsDTO;
import com.lovezz.entity.TbComments;
import com.lovezz.entity.TbGallery;
import com.lovezz.entity.TbTops;
import com.lovezz.mapper.TbCommentsMapper;
import com.lovezz.mapper.TbGalleryMapper;
import com.lovezz.mapper.TbTopsMapper;
import com.lovezz.service.TbTopsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lovezz.utils.OssUtil;
import com.lovezz.utils.RequestUtils;
import com.lovezz.utils.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2019-11-03
 */
@Service
@Transactional
public class TbTopsServiceImpl extends ServiceImpl<TbTopsMapper, TbTops> implements TbTopsService {

    //文件上传路径文件夹
    private static final String TOPS_DIR = "topsImg/";

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private TbTopsMapper topsMapper;

    @Autowired
    private TbGalleryMapper galleryMapper;

    @Autowired
    private TbCommentsMapper commentsMapper;


    @Override
    public boolean publishTops(MultipartFile[] fileList, String topText) {

        String host = null;
        try {
            TbTops tops = new TbTops();
            String id = UUID.randomUUID().toString().replace("-", "");
            tops.setId(id);
            tops.setContent(topText);
            tops.setUserId(new RequestUtils().getLoginUserId());
            topsMapper.insert(tops);

            for (MultipartFile file : fileList) {
                String url = ossUtil.checkImage(file, TOPS_DIR);
                //无参数的连接 http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg
                host = URLUtils.getPath(url);

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
                gallery.setUserId(new RequestUtils().getLoginUserId());
                gallery.setTopId(id);
                //设置图片用途为留言板
                gallery.setFlag("1");

                galleryMapper.insert(gallery);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

    @Override
    public List<TopsDTO> getTopsList() {


        List<TbTops> topsList = topsMapper.selectTopList();
        List<TopsDTO> dtoList = new ArrayList<>();

        List<TbGallery> galleryList = null;
        List<TbComments> commentsList = null;

        for (TbTops tops : topsList) {
            TopsDTO topsDTO = new TopsDTO();


            //添加图片
            galleryList = galleryMapper.selectList(new EntityWrapper<TbGallery>().eq("topId", tops.getId()).eq("flag", "1"));
            if (galleryList != null && galleryList.size() > 0){
                topsDTO.setGalleryList(galleryList);
            }

            //添加评论
            commentsList =  commentsMapper.selectList(new EntityWrapper<TbComments>().eq("topId", tops.getId())
                    .eq("state", "0").eq("isDelete", "0"));
            if (commentsList != null && commentsList.size() > 0){
                topsDTO.setCommentsList(commentsList);
            }
            topsDTO.setTops(tops);
            dtoList.add(topsDTO);
        }


        return dtoList;
    }

    @Override
    public BaseResult deleteTops(String topsId) {

        TbTops tops = topsMapper.selectById(topsId);
        if (tops == null){
            return BaseResult.fail("删除的话题不存在哦");
        }else {
            //设置标记
            tops.setIsDelete("1");
            topsMapper.updateById(tops);

            //删除图库记录
            List<TbGallery> galleryList = galleryMapper.selectList(new EntityWrapper<TbGallery>().eq("topId", topsId));
            List<String> keyList = new ArrayList<>();
            for (TbGallery gallery : galleryList) {
                keyList.add(ossUtil.getUrlPath(gallery.getUrl()));
            }
            galleryMapper.delete(new EntityWrapper<TbGallery>().eq("topId", topsId));
            ossUtil.deleteBatchFile(keyList);


            //删除评论
            List<TbComments> commentsList = commentsMapper.selectList(new EntityWrapper<TbComments>().eq("topId", topsId));
            for (TbComments comments : commentsList) {
                comments.setIsDelete("1");
                commentsMapper.updateById(comments);
            }

            return BaseResult.success();
        }
    }
}
