package com.qltime.model.dto;

/**
 * @author liweijian
 * @Date: 2019/10/13 10:15
 * @Description:
 */
public class ImageInfoDTO {
    private FileSize FileSize;

    private Format Format;

    private ImageHeight ImageHeight;

    private ImageWidth ImageWidth;

    public void setFileSize(FileSize FileSize) {
        this.FileSize = FileSize;
    }

    public FileSize getFileSize() {
        return this.FileSize;
    }

    public void setFormat(Format Format) {
        this.Format = Format;
    }

    public Format getFormat() {
        return this.Format;
    }

    public void setImageHeight(ImageHeight ImageHeight) {
        this.ImageHeight = ImageHeight;
    }

    public ImageHeight getImageHeight() {
        return this.ImageHeight;
    }

    public void setImageWidth(ImageWidth ImageWidth) {
        this.ImageWidth = ImageWidth;
    }

    public ImageWidth getImageWidth() {
        return this.ImageWidth;
    }
}
