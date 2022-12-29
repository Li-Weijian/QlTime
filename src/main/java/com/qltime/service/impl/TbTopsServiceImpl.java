package com.qltime.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qltime.constant.GalleryFlagEnum;
import com.qltime.constant.SystemConstants;
import com.qltime.dto.BaseResult;
import com.qltime.dto.TopsDTO;
import com.qltime.entity.TbComments;
import com.qltime.entity.TbGallery;
import com.qltime.entity.TbTops;
import com.qltime.exception.CommonException;
import com.qltime.mapper.TbCommentsMapper;
import com.qltime.mapper.TbGalleryMapper;
import com.qltime.mapper.TbTopsMapper;
import com.qltime.service.TbGalleryService;
import com.qltime.service.TbTopsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qltime.service.TbUserService;
import com.qltime.utils.OssUtil;
import com.qltime.utils.RequestUtils;
import com.qltime.utils.URLUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.ArrayList;
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


    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private TbTopsMapper topsMapper;

    @Autowired
    private TbGalleryMapper galleryMapper;

    @Autowired
    private TbCommentsMapper commentsMapper;

    @Autowired
    private TbUserService userService;

    @Autowired
    private TbGalleryService galleryService;

    List<TbComments> resultList = new ArrayList<>();


    @Override
    public boolean publishTops(MultipartFile[] fileList, String topText) {

        String host = null;
        try {
            TbTops tops = makeTops(topText);

            for (MultipartFile file : fileList) {
                String url = ossUtil.checkImage(file, SystemConstants.TOPS_DIR);
                //无参数的连接 http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg
                host = URLUtils.getPath(url);

                galleryService.makeGallery(host, tops.getId(),String.valueOf(GalleryFlagEnum.TOPS.getType()), "Topsfile");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

    @Override
    public boolean publishTops(List<String> fileList, String topText) throws MalformedURLException {
        TbTops tops = makeTops(topText);
        for (String fileUrl : fileList) {
            galleryService.makeGallery(fileUrl, tops.getId(),String.valueOf(GalleryFlagEnum.TOPS.getType()), "Topsfile");
        }
        return true;
    }

    private TbTops makeTops(String topText){
        TbTops tops = new TbTops();
        String id = UUID.randomUUID().toString().replace("-", "");
        tops.setId(id);
        tops.setContent(topText);
        tops.setUserId(new RequestUtils().getLoginUserId());
        topsMapper.insert(tops);
        return tops;
    }


    @Override
    public List<TopsDTO> getTopsList(Integer userId) throws CommonException {

        return this.getTopsList(null, null, userService.selectAllIds(userId));
    }

    @Override
    public List<TopsDTO> getTopsList(Integer offset, Integer limit, List<Integer> ids) {
        List<TbTops> topsList = topsMapper.selectTopList(offset, limit, ids);
        List<TopsDTO> dtoList = new ArrayList<>();

        List<TbGallery> galleryList = null;

        for (TbTops tops : topsList) {
            TopsDTO topsDTO = new TopsDTO();
            List<List<TbComments>> commentsList = new ArrayList<>();

            //查询图片
            galleryList = galleryMapper.selectList(new EntityWrapper<TbGallery>().eq("topId", tops.getId()).eq("flag", "1"));
            if (galleryList != null && galleryList.size() > 0){
                topsDTO.setGalleryList(galleryList);
            }

            //查询评论
            List<TbComments> comments = this.commentsMapper.selectList(new EntityWrapper<TbComments>().eq("topId", tops.getId()).eq("isDelete", "0")
                    .orderBy("created", true));
            for (TbComments comm : comments) {
                List<TbComments> replayList = new ArrayList<>();
                String userName = userService.selectUserName(comm.getUserId());
                comm.setContent(userName+" 回复："+comm.getContent());
                replayList.add(comm);
                replayList.addAll(selectCommentsByTopId(comm.getId()));
                commentsList.add(replayList);
                resultList.clear();
            }

            topsDTO.setCommentsList(commentsList);
            topsDTO.setTops(tops);
            dtoList.add(topsDTO);
        }

        return dtoList;
    }


    // 根据说说id构建属于该说说的评论列表
    private List<TbComments> selectCommentsByTopId(String topsId){
        List<TbComments> replayList = this.commentsMapper.selectList(new EntityWrapper<TbComments>().eq("lastId", topsId).eq("isDelete", "0")
                .orderBy("created", true));

        String userName;
        String replayUserName;

        if (replayList != null &&  replayList.size() > 0){
            for (TbComments comments : replayList) {
                userName = userService.selectUserName(comments.getUserId());
                replayUserName = userService.selectUserName(comments.getReplayUserId());
                comments.setContent(userName+" 回复 "+replayUserName + ":"+ comments.getContent());
                resultList.add(comments);
                this.selectCommentsByTopId(comments.getId());
            }
        }

         return resultList;
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

    @Override
    public BaseResult doCommont(String topId, String content, String flag) {
        TbComments comments = new TbComments();
        comments.setIsDelete("0");
        comments.setFlag(flag);
        comments.setUserId(new RequestUtils().getLoginUserId());
        comments.setFlag(flag);
        comments.setContent(content);

        //回复说说
        if (StringUtils.isNotBlank(flag) && "0".equals(flag)){
            comments.setTopId(topId);
            comments.setLastId("");
            TbTops tops = this.selectById(topId);
            comments.setReplayUserId(tops.getUserId());

        }else if (StringUtils.isNotBlank(flag) && "1".equals(flag)){
            //回复评论
            comments.setTopId("");
            comments.setLastId(topId);
            TbComments comm = this.commentsMapper.selectById(topId);
            comments.setReplayUserId(comm.getUserId());
        }

        this.commentsMapper.insert(comments);

        return BaseResult.success();
    }

    @Override
    public List<String> uploadTopsImages(MultipartFile[] fileList) throws MalformedURLException {

        List<String> resultList = new ArrayList<>();

        for (MultipartFile file : fileList) {
            String url = ossUtil.checkImage(file, SystemConstants.TOPS_DIR);
            //无参数的连接 http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg
            resultList.add(URLUtils.getPath(url));
        }
        return resultList;
    }
}
