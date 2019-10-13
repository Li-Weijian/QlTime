package com.lovezz.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liweijian123
 * @since 2019-10-12
 */
@TableName("tb_gallery")
public class TbGallery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String filesize;
    /**
     * 后缀格式
     */
    private String format;
    @TableField("imageHeight")
    private Integer imageHeight;
    @TableField("imageWidth")
    private Integer imageWidth;
    private String url;
    @TableField("uploadDate")
    private Date uploadDate;
    private String fileName;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "TbGallery{" +
        ", id=" + id +
        ", filesize=" + filesize +
        ", format=" + format +
        ", imageHeight=" + imageHeight +
        ", imageWidth=" + imageWidth +
        ", url=" + url +
        ", uploadDate=" + uploadDate +
        "}";
    }
}
