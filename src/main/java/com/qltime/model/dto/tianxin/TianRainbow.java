package com.qltime.model.dto.tianxin;

import lombok.Data;

import java.util.List;

/**
 * @author liweijian
 * @date 2022/12/29 18:57
 */
@Data
public class TianRainbow {

    private Integer code;

    private String msg;

    private List<Result> newslist;

    @Data
    public static class Result {
        private  String content;
    }
}
