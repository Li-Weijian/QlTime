package com.qltime.constant;

/**
 * 图库用途类型
 * @author liweijian
 * @Date: 2020/9/29 09:46
 * @Description:
 */
public enum GalleryFlagEnum {

    // -1 临时 0- 图库  1-留言板
    TEMP(-1, "临时"),
    GALLERY(0, "图库"),
    TOPS(1, "留言板"),
    VISIT_RECORD(2, "探店记录"),
    ;

    /**
     * 编号
     */
    private Integer type;

    /**
     * 说明
     */
    private String desc;

    private GalleryFlagEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * 根据类型编号获取相应的说明
     *
     * @param type
     * @return
     */
    public static GalleryFlagEnum getRecordStatus(Integer type) {
        for (GalleryFlagEnum one : GalleryFlagEnum.values()) {
            if (type .equals(one.getType())) {
                return one;
            }
        }
        return GalleryFlagEnum.TEMP;
    }

    public Integer getType() {
        return type;
    }

    public String getTypeStr(){
        return String.valueOf(getType());
    }
    public String getDesc() {
        return desc;
    }

}
