package com.qltime.model.dto.tianxin;
import lombok.Data;

import java.util.List;

/**
 * 星座实体
 * @author liweijian
 */
@Data
public class TianStar {
    private int code;
    private String msg;
    private List<TianStar.Result> newslist;

    @Data
    public static class Result {
        private String type;
        private String content;
    }

}
