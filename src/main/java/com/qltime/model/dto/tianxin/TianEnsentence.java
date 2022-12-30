package com.qltime.model.dto.tianxin;

import lombok.Data;

import java.util.List;

/**
 *
 * 天行 每日一句
 * @author liweijian
 * @date 2022/12/29 19:05
 */
@Data
public class TianEnsentence {

    private Integer code;

    private String msg;

    private List<Result> newslist;

    @Data
    public static class Result {

//              "id": 3673,
//              "content": "Solitude is the soul’s holiday, an opportunity to stop doing for others and to surprise and delight ourselves instead.",
//              "note": "独处是灵魂的假期，你可以不为他人奔忙，只为给自己带来惊喜与喜悦。",
//              "source": "American drama lines",
//              "date": "2020-02-22"

        private String id;

        private String content;

        private String note;

        private String source;

        private String date;
    }
}
