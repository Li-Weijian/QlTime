package com.qltime.exception;

import com.qltime.model.dto.BaseResult;
import lombok.Data;

/**
 * @auther: liweijian
 * @Date: 2020/9/26 16:43
 * @Description:
 */
@Data
public class CommonException extends Exception {

    private int status;
    private String message;


    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
        this.message = message;
    }

    public CommonException(Integer status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public CommonException(BaseResult baseResult) {
        super();
        this.message = baseResult.getMessage();
        this.status = baseResult.getStatus();
    }
}
