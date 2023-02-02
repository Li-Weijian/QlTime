package com.qltime.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义返回结果
 * <p>Title: BaseResult</p>
 * <p>Description: </p>
 *
 * @author liweijian
 */
@Data
public class BaseResult<T> implements Serializable {
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_FAIL = 500;

    private int status;
    private String message;
    private T data;

    public static <T> BaseResult<T> success() {
        return BaseResult.createResult(STATUS_SUCCESS, "成功", null);
    }

    public static <T> BaseResult<T> success(String message) {
        return BaseResult.createResult(STATUS_SUCCESS, message, null);
    }

    public static <T> BaseResult<T> success(T data) {
        return BaseResult.createResult(STATUS_SUCCESS, "成功", data);
    }

    public static <T> BaseResult<T> success(String message, T data) {
        return BaseResult.createResult(STATUS_SUCCESS, message, data);
    }

    public static <T> BaseResult<T> fail() {
        return BaseResult.createResult(STATUS_FAIL, "失败", null);
    }

    public static <T> BaseResult<T> fail(String message) {
        return BaseResult.createResult(STATUS_FAIL, message, null);
    }

    public static <T> BaseResult<T> fail(int status, String message) {
        return BaseResult.createResult(status, message, null);
    }


    private static <T> BaseResult<T> createResult(int status, String message, T data) {
        BaseResult<T> baseResult = new BaseResult<T>();
        baseResult.setStatus(status);
        baseResult.setMessage(message);
        baseResult.setData(data);
        return baseResult;
    }

    public BaseResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResult() {
    }
}
