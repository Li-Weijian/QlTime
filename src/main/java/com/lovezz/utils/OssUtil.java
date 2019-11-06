
package com.lovezz.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.*;
import com.lovezz.dto.ImageInfoDTO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description: 阿里云OSS服务器工具类
 * @author: liweijian
 * @date: 2019/10/11 16:26
 */
@Component
public class OssUtil {

    protected static final Logger log = LoggerFactory.getLogger(OssUtil.class);

    @Value("${aliyun.endpoint}")
    private String endpoint;
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.bucketName}")
    private String bucketName;

    //文件存储目录
    @Value("${aliyun.fileDir}")
    private String fileDir;

    @Autowired
    private RestTemplate restTemplate;


    /**
     *
     * 上传图片
     * @param file
     * @return
     */
    public String uploadImg2Oss(MultipartFile file,String fileDir) {
        if (file.getSize() > 1024 * 1024 *20) {
            return "图片太大";//RestResultGenerator.createErrorResult(ResponseEnum.PHOTO_TOO_MAX);
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name,fileDir);
            return name;//RestResultGenerator.createSuccessResult(name);
        } catch (Exception e) {
            return "上传失败";//RestResultGenerator.createErrorResult(ResponseEnum.PHOTO_UPLOAD);
        }
    }

    /**
     * 上传图片获取fileUrl
     * @param instream
     * @param fileName
     * @param fileDir
     * @return
     */
    private String uploadFile2OSS(InputStream instream, String fileName, String fileDir) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

//            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = ossClient.putObject(bucketName, fileDir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获取图片路径
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
      return getImgUrl(fileUrl,this.fileDir);
    }

    public String getImgUrl(String fileUrl,String fileDir) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            String url =  this.getUrl(fileDir + split[split.length - 1]);
            return url;
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    /**
     * 多图片上传
     * @param fileList
     * @return
     */
    public String checkList(List<MultipartFile> fileList) {
        return checkList(fileList,this.fileDir);

    }

    public String checkList(List<MultipartFile> fileList,String fileDir) {
        String  fileUrl = "";
        String  str = "";
        String  photoUrl = "";
        for(int i = 0;i< fileList.size();i++){
            fileUrl = uploadImg2Oss(fileList.get(i),fileDir);
            str = getImgUrl(fileUrl);
            if(i == 0){
                photoUrl = str;
            }else {
                photoUrl += "," + str;
            }
        }
        return photoUrl.trim();
    }

    /**
     * 单个图片上传
     * @param file
     * @return
     */
    public String checkImage(MultipartFile file){
        return checkImage(file,this.fileDir);
    }

    public String checkImage(MultipartFile file, String fileDir){
        String fileUrl = uploadImg2Oss(file,fileDir);
        String str = getImgUrl(fileUrl,fileDir);
        return str.trim();
    }
    
    /**
     * 功能描述: 获取图片信息
     *
     * @param: url = http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg
     * @return: 
     * @auther: liweijian
     * @date: 2019/10/13 10:13
     */
    public ImageInfoDTO getImageInfo(String url){
       String style = "image/info";
       url = url + "?x-oss-process=image/info";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String strBody=restTemplate.exchange(url, HttpMethod.GET, entity,String.class).getBody();
        ImageInfoDTO infoDTO = JSONObject.parseObject(strBody, ImageInfoDTO.class);

        return infoDTO;
    }

/**
 * 批量删除文件
 * key等同于ObjectName，表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
 * @param: keyList  key等同于ObjectName，表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
 * @return: 删除的文件对象
 * @auther: liweijian
 * @date: 2019/11/6 20:50
 */
    public List<String> deleteBatchFile(List<String> keyList){
        List<String> deletedObjects = null;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        if (keyList != null && keyList.size() > 0){
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keyList));
            deletedObjects = deleteObjectsResult.getDeletedObjects();
        }

        ossClient.shutdown();
        return deletedObjects;
    }

    /**
     * 获取Url路径
     * @param: url ps: http://lovezz-app.oss-cn-shenzhen.aliyuncs.com/userImg/1570960002838.jpg
     * @return: url路径 ps：/userImg/1570960002838.jpg
     * @auther: liweijian
     * @date: 2019/11/6 20:55
     */
    public String getUrlPath(String url){
        String path = null;
        try {
            URL u = new URL(url);
            path = u.getPath().substring(1,u.getPath().length());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return path;
    }

   /* public static void main(String[] args) {
        OssUtil ossUtil = new OssUtil();
        ossUtil.getUrlPath("http://lovezz-app.oss-cn-shenzhen.aliyuncs.com/userImg/1570960002838.jpg?asjdkja=12312");

    }*/

}